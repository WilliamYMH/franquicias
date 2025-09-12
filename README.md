# API de Franquicias

API RESTful para la gestión de franquicias, sucursales y productos, desarrollada con Spring Boot, WebFlux (programación reactiva) y arquitectura hexagonal.

## Características

- Arquitectura Hexagonal (Puertos y Adaptadores)
- Programación Reactiva con Spring WebFlux
- Programación Funcional
- Principios SOLID
- Clean Code
- Base de datos H2 en memoria
- Documentación con OpenAPI/Swagger

## Estructura del Proyecto

La aplicación sigue una arquitectura hexagonal con las siguientes capas:

- **Dominio**: Contiene las entidades del negocio y los puertos (interfaces)
- **Aplicación**: Contiene los casos de uso (servicios) que implementan la lógica de negocio
- **Infraestructura**: Contiene los adaptadores que conectan con el mundo exterior (REST, base de datos, etc.)

## Modelo de Datos

- **Franquicia**: Entidad principal que contiene un nombre y una lista de sucursales
- **Sucursal**: Entidad que contiene un nombre y una lista de productos
- **Producto**: Entidad que contiene un nombre y una cantidad de stock

## Requisitos

- Java 21
- Gradle

## Ejecución

Para ejecutar la aplicación, sigue estos pasos:

1. Clona el repositorio
2. Navega al directorio del proyecto
3. Ejecuta el siguiente comando:

```bash
./gradlew bootRun
```

La aplicación estará disponible en `http://localhost:8080`

## Documentación de la API

La documentación de la API está disponible en:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Endpoints

### Franquicias

- `POST /api/franquicias`: Crear una nueva franquicia
- `GET /api/franquicias`: Obtener todas las franquicias
- `GET /api/franquicias/{id}`: Obtener una franquicia por su ID
- `PUT /api/franquicias/{id}`: Actualizar una franquicia existente
- `DELETE /api/franquicias/{id}`: Eliminar una franquicia

### Sucursales

- `POST /api/sucursales/franquicia/{franquiciaId}`: Crear una nueva sucursal para una franquicia
- `GET /api/sucursales/{id}`: Obtener una sucursal por su ID
- `GET /api/sucursales/franquicia/{franquiciaId}`: Obtener todas las sucursales de una franquicia
- `PUT /api/sucursales/{id}`: Actualizar una sucursal existente
- `DELETE /api/sucursales/{id}`: Eliminar una sucursal

### Productos

- `POST /api/productos/sucursal/{sucursalId}`: Crear un nuevo producto para una sucursal
- `GET /api/productos/{id}`: Obtener un producto por su ID
- `GET /api/productos/sucursal/{sucursalId}`: Obtener todos los productos de una sucursal
- `PUT /api/productos/{id}`: Actualizar un producto existente
- `PATCH /api/productos/{id}/stock/{cantidad}`: Actualizar el stock de un producto
- `DELETE /api/productos/{id}`: Eliminar un producto

## Consola H2

La consola de la base de datos H2 está disponible en:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:franquicias`
- Usuario: `sa`
- Contraseña: (dejar en blanco)
