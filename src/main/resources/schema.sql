-- Tabla para franquicias
CREATE TABLE IF NOT EXISTS franquicias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

-- Tabla para sucursales
CREATE TABLE IF NOT EXISTS sucursales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    franquicia_id BIGINT NOT NULL,
    FOREIGN KEY (franquicia_id) REFERENCES franquicias(id)
);

-- Tabla para productos
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    stock INT NOT NULL,
    sucursal_id BIGINT NOT NULL,
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id)
);
