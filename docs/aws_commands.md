# AWS Commands Cheat Sheet (Proyecto Franquicias)

Guía rápida con los comandos que usamos (o podemos usar) para configurar AWS, construir y desplegar la app con Terraform y Docker Hub. Están ordenados desde el inicio hasta el despliegue y la limpieza.

Nota: Reemplaza los valores entre <> por los tuyos cuando aplique. En este proyecto usamos:
- Región: `us-east-1`
- Perfil AWS sugerido: `franquicias`
- Key Pair EC2: `franquicias-key`
- Usuario Docker Hub: `williamdock1`

---

## 1) Instalación de herramientas en macOS

```bash
# Homebrew (si no lo tienes)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# AWS CLI v2
brew install awscli

# Terraform
brew install terraform

# Verificar versiones
aws --version
terraform -version
```

---

## 2) Configurar credenciales y perfil AWS

```bash
# Configurar perfil 'franquicias' (ingresa tu Access Key y Secret)
aws configure --profile franquicias
# Sugerido:
#   Default region name: us-east-1
#   Default output format: json

# Usar el perfil en la sesión actual
export AWS_PROFILE=franquicias
export AWS_REGION=us-east-1

# Verificar configuración y credenciales
aws configure list --profile franquicias
aws sts get-caller-identity
```

---

## 3) Key Pair de EC2 (SSH)

```bash
# Ver key pairs existentes en la región
aws ec2 describe-key-pairs \
  --region us-east-1 \
  --query 'KeyPairs[].KeyName' \
  --output table

# Crear un key pair nuevo (se guardará el .pem localmente)
aws ec2 create-key-pair \
  --region us-east-1 \
  --profile franquicias \
  --key-name franquicias-key \
  --query 'KeyMaterial' \
  --output text > ~/franquicias-key.pem

# Permisos seguros para la llave
chmod 400 ~/franquicias-key.pem
```

Asegúrate de poner `key_name = "franquicias-key"` en `infra-terraform/terraform.tfvars`.

---

## 4) Confirmar tu IP pública para SSH (CIDR /32)

```bash
# Obtén tu IP pública y úsala como allowed_ssh_cidr (p.ej., 203.0.113.10/32)
curl -s https://checkip.amazonaws.com
```

Pon el valor en `infra-terraform/terraform.tfvars`:
```
allowed_ssh_cidr = "<TU_IP_PUBLICA>/32"
```

---

## 5) Preparar y subir imagen a Docker Hub

```bash
# Login en Docker Hub
docker login

# Construir imagen desde la raíz del proyecto (donde está el Dockerfile)
cd /Users/william.moreno/Documents/dev/franquicias

# Tag con tu usuario de Docker Hub
docker build -t williamdock1/franquicias:latest .

# (Opcional) Tag versionado para despliegues repetibles
# docker build -t williamdock1/franquicias:v0.1 -t williamdock1/franquicias:latest .

# Subir imagen a Docker Hub
# Último tag
docker push williamdock1/franquicias:latest
# (Opcional) Tag versionado
# docker push williamdock1/franquicias:v0.1
```

Asegúrate de poner en `infra-terraform/terraform.tfvars`:
```
docker_image = "williamdock1/franquicias:latest"
# o versionado
# docker_image = "williamdock1/franquicias:v0.1"
```

---

## 6) Terraform: inicializar, planificar y aplicar

```bash
# Ir al directorio de IaC dentro del repo
cd /Users/william.moreno/Documents/dev/franquicias/infra-terraform

# Inicializar Terraform (descarga proveedores, etc.)
terraform init

# Ver plan de cambios (revisar detenidamente)
terraform plan -out plan.tfplan

# Aplicar el plan (crea los recursos en AWS)
terraform apply plan.tfplan
```

Outputs esperados:
- `app_public_ip`: IP pública de la instancia EC2
- `db_endpoint`: endpoint de la base de datos RDS MySQL

---

## 7) Acceder a la aplicación

```text
Swagger UI:  http://<APP_PUBLIC_IP>:8080/v3/webjars/swagger-ui/index.html#/
API Docs:    http://<APP_PUBLIC_IP>:8080/api-docs
```

---

## 8) Comandos útiles de diagnóstico (opcionales)

```bash
# Ver logs en la ec2
docker logs --tail 400 franquicias-app
# Ver tipos de instancia EC2 elegibles para Free Tier en la región
aws ec2 describe-instance-types \
  --region us-east-1 \
  --filters "Name=free-tier-eligible,Values=true" \
  --query "InstanceTypes[].InstanceType" \
  --output table

# Ver key pairs para confirmar nombre correcto
aws ec2 describe-key-pairs --region us-east-1 --query 'KeyPairs[].KeyName' --output table

# Mostrar outputs de Terraform luego del apply
terraform output
```

Notas de compatibilidad:
- Si RDS falla por combinación no soportada, usar `db.t3.micro` y permitir que AWS elija la versión de MySQL (sin `engine_version`).
- Si EC2 marca no Free Tier, usar `t3.micro` (x86_64) en `aws_instance.app`.

---

## 9) Limpieza de recursos (al finalizar)

```bash
# Destruir la infraestructura creada por Terraform (evita costos)
cd /Users/william.moreno/Documents/dev/franquicias/infra-terraform
terraform destroy
```

(Opcional) Rotar credenciales IAM creadas para el proyecto o eliminar la access key usada.

---

## 10) (Opcional) Alternativa con Amazon ECR en lugar de Docker Hub

```bash
# Crear repositorio en ECR
aws ecr create-repository --repository-name franquicias --region us-east-1

# Obtener tu Account ID
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)

# Login en ECR
aws ecr get-login-password --region us-east-1 | \
  docker login --username AWS --password-stdin ${ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com

# Construir y etiquetar
docker build -t franquicias:latest .
docker tag franquicias:latest ${ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/franquicias:latest

# Subir al repositorio ECR
docker push ${ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/franquicias:latest

# En terraform.tfvars
# docker_image = "${ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/franquicias:latest"
```

---

## Referencias rápidas
- Perfil AWS por sesión: `export AWS_PROFILE=franquicias` y `export AWS_REGION=us-east-1`
- Archivo de variables: `infra-terraform/terraform.tfvars`
- Directorio IaC: `infra-terraform/`
- Despliegue app: EC2 (Docker) + RDS MySQL
