variable "aws_region" {
  description = "AWS region to deploy resources into"
  type        = string
  default     = "us-east-1"
}

variable "allowed_ssh_cidr" {
  description = "CIDR block allowed to SSH into the EC2 instance (use your IP/32)"
  type        = string
  default     = "0.0.0.0/0"
}

variable "app_port" {
  description = "Port the application listens on"
  type        = number
  default     = 8080
}

variable "db_username" {
  description = "MySQL database username"
  type        = string
  sensitive   = true
}

variable "db_password" {
  description = "MySQL database password"
  type        = string
  sensitive   = true
}

variable "key_name" {
  description = "Existing EC2 key pair name for SSH access"
  type        = string
}

variable "docker_image" {
  description = "Container image for the application (e.g. dockerhub_user/franquicias:latest)"
  type        = string
}
