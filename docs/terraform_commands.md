# Terraform Commands

Guía práctica para ejecutar Terraform en este proyecto (`infra-terraform/`).

Ruta: `infra-terraform/`

---

## 1) Primera vez: levantar toda la infraestructura

1. Preparar entorno (si usas perfil AWS)
```bash
export AWS_PROFILE=franquicias
export AWS_REGION=us-east-1
```

2. Crear tu archivo de variables reales a partir del ejemplo
```bash
cd infra-terraform
cp terraform.tfvars.example terraform.tfvars
# Edita terraform.tfvars con tus valores (allowed_ssh_cidr, key_name, docker_image, etc.)
```

3. Inicializar Terraform (descarga providers y configura backend local)
```bash
terraform init
```

4. Revisar el plan de cambios
```bash
terraform plan -out plan.tfplan
```

5. Aplicar el plan (crea los recursos)
```bash
terraform apply plan.tfplan
```

6. Outputs útiles
```bash
terraform output
# app_public_ip -> IP pública de EC2
# db_endpoint   -> endpoint de RDS MySQL
```

---

## 2) Volver a aplicar cambios después de editar `main.tf` o `variables.tf`

1. Preparar entorno (si usas perfil AWS)
```bash
export AWS_PROFILE=franquicias
export AWS_REGION=us-east-1
cd infra-terraform
```

2. Ver el plan con los cambios
```bash
terraform plan -out plan.tfplan
```

3. Aplicar los cambios
```bash
terraform apply plan.tfplan
```

> Nota: Si modificaste el script `user_data` o la definición de la instancia EC2 y quieres forzar su recreación (para que re-aplique el `docker-compose` generado), usa `-replace`:
```bash
terraform plan -replace=aws_instance.app -out plan.tfplan
terraform apply plan.tfplan
```

---

## 3) Cuando actualizas la imagen Docker de la app

Terraform no reconstruye imágenes. Pasos recomendados:
```bash
# En tu máquina local, desde la raíz del proyecto
export DOCKER_DEFAULT_PLATFORM=linux/amd64
docker login
cd /Users/william.moreno/Documents/dev/franquicias

docker build -t williamdock1/franquicias:latest .
docker push williamdock1/franquicias:latest
```
Luego:
- Si la EC2 ya existe: entrar por SSH y ejecutar en `/home/ec2-user`:
```bash
/usr/local/bin/docker-compose pull
/usr/local/bin/docker-compose up -d
```
- Si prefieres que Terraform reprovisione la EC2 con el `user_data` actualizado, usa el replace:
```bash
cd infra-terraform
terraform plan -replace=aws_instance.app -out plan.tfplan
terraform apply plan.tfplan
```

---

## 4) Destruir infraestructura (limpieza)

```bash
cd infra-terraform
terraform destroy
```

---

## 5) Tips y validaciones rápidas

```bash
# Verificar credenciales
aws sts get-caller-identity

# Listar key pairs (us-east-1)
aws ec2 describe-key-pairs --region us-east-1 --query 'KeyPairs[].KeyName' --output table

# Verificar instancia EC2 por etiqueta
aws ec2 describe-instances --filters "Name=tag:Name,Values=franquicias-app" --query "Reservations[].Instances[].InstanceId" --output text

# Ver outputs después de apply
terraform output
```
