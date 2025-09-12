-- Tabla para franquicias
CREATE TABLE IF NOT EXISTS franquicias (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

-- Tabla para sucursales
CREATE TABLE IF NOT EXISTS sucursales (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    franquicia_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (franquicia_id) REFERENCES franquicias(id)
);

-- Tabla para productos
CREATE TABLE IF NOT EXISTS productos (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    stock INT NOT NULL,
    sucursal_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (sucursal_id) REFERENCES sucursales(id)
);
