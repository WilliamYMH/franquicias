terraform {
  required_version = ">= 1.5.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = ">= 5.0"
    }
  }
}

provider "aws" {
  region  = var.aws_region
  profile = var.aws_profile
}

############################
# Networking (VPC & Subnets)
############################
resource "aws_vpc" "main" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_support   = true
  enable_dns_hostnames = true
  tags = { Name = "franquicias-vpc" }
}

resource "aws_subnet" "public" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.1.0/24"
  availability_zone       = "${var.aws_region}a"
  map_public_ip_on_launch = true
  tags = { Name = "franquicias-public" }
}

resource "aws_subnet" "private" {
  count             = 2
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.${count.index + 10}.0/24"
  availability_zone = "${var.aws_region}${count.index == 0 ? "a" : "b"}"
  tags = { Name = "franquicias-private-${count.index}" }
}

resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.main.id
  tags   = { Name = "franquicias-igw" }
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.main.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }
  tags = { Name = "franquicias-public-rt" }
}

resource "aws_route_table_association" "public_assoc" {
  subnet_id      = aws_subnet.public.id
  route_table_id = aws_route_table.public.id
}

######################
# Security Groups
######################
resource "aws_security_group" "ec2_sg" {
  name   = "franquicias-ec2-sg"
  vpc_id = aws_vpc.main.id

  # SSH (restringe en producción)
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = [var.allowed_ssh_cidr]
  }

  # App HTTP on port 80 (public)
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = { Name = "franquicias-ec2-sg" }
}

resource "aws_security_group" "rds_sg" {
  name   = "franquicias-rds-sg"
  vpc_id = aws_vpc.main.id

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [aws_security_group.ec2_sg.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = { Name = "franquicias-rds-sg" }
}

############################
# RDS MySQL (db.t2.micro)
############################
resource "aws_db_subnet_group" "db_subnet" {
  name       = "franquicias-db-subnet"
  subnet_ids = aws_subnet.private[*].id
  tags       = { Name = "Franquicias DB Subnet Group" }
}

resource "aws_db_instance" "mysql" {
  identifier              = "franquicias-db"
  allocated_storage       = 20
  storage_type            = "gp2"
  engine                  = "mysql"
  instance_class          = "db.t3.micro" # Free tier eligible in most regions
  db_name                 = "franquicias"
  username                = var.db_username
  password                = var.db_password
  parameter_group_name    = "default.mysql8.0"
  db_subnet_group_name    = aws_db_subnet_group.db_subnet.name
  vpc_security_group_ids  = [aws_security_group.rds_sg.id]
  skip_final_snapshot     = true
  deletion_protection     = false
  publicly_accessible     = false
  multi_az                = false

  tags = { Name = "franquicias-mysql" }
}

############################################
# EC2 t2.micro (Docker host para la app)
############################################
# AMI Amazon Linux 2 más reciente
data "aws_ami" "al2" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn2-ami-hvm-*-x86_64-gp2"]
  }
}

resource "aws_instance" "app" {
  ami                         = data.aws_ami.al2.id
  instance_type               = "t3.micro"
  subnet_id                   = aws_subnet.public.id
  vpc_security_group_ids      = [aws_security_group.ec2_sg.id]
  key_name                    = var.key_name
  associate_public_ip_address = true
  depends_on                  = [aws_db_instance.mysql]

  user_data = <<-EOF
              #!/bin/bash
              set -eux
              yum update -y
              amazon-linux-extras install docker -y || yum install docker -y
              systemctl enable docker
              systemctl start docker
              usermod -a -G docker ec2-user || true

              # Instalar docker-compose
              curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
              chmod +x /usr/local/bin/docker-compose

              # Esperar a que RDS (MySQL) esté disponible en el puerto 3306
              DB_HOST="${aws_db_instance.mysql.endpoint}"
              echo "Esperando a que MySQL en ${aws_db_instance.mysql.endpoint}:3306 esté disponible..."
              for i in {1..60}; do
                if bash -c "</dev/tcp/${aws_db_instance.mysql.endpoint}/3306" >/dev/null 2>&1; then
                  echo "MySQL alcanzable. Continuando..."
                  break
                fi
                echo "Intento $i/60: MySQL aún no disponible, reintentando en 5s..."
                sleep 5
              done

              cat > /home/ec2-user/docker-compose.yml <<'EOL'
              version: '3.8'
              services:
                app:
                  platform: linux/amd64
                  image: ${var.docker_image}
                  container_name: franquicias-app
                  ports:
                    - "80:${var.container_port}"
                  environment:
                    - SPRING_PROFILES_ACTIVE=prod
                    - SPRING_R2DBC_URL=r2dbc:mysql://${aws_db_instance.mysql.endpoint}/${aws_db_instance.mysql.db_name}
                    - SPRING_R2DBC_USERNAME=${var.db_username}
                    - SPRING_R2DBC_PASSWORD=${var.db_password}
                  restart: unless-stopped
              EOL

              chown ec2-user:ec2-user /home/ec2-user/docker-compose.yml
              cd /home/ec2-user
              /usr/local/bin/docker-compose up -d
              EOF

  tags = { Name = "franquicias-app" }
}
