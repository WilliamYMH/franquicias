-- Datos iniciales para franquicias
INSERT INTO franquicias (nombre) VALUES 
('Franquicia Ejemplo 1'),
('Franquicia Ejemplo 2');

-- Datos iniciales para sucursales (usando subconsultas para obtener los IDs de franquicias)
INSERT INTO sucursales (nombre, franquicia_id) VALUES 
('Sucursal Central', (SELECT id FROM franquicias WHERE nombre = 'Franquicia Ejemplo 1' LIMIT 1)),
('Sucursal Norte', (SELECT id FROM franquicias WHERE nombre = 'Franquicia Ejemplo 1' LIMIT 1)),
('Sucursal Principal', (SELECT id FROM franquicias WHERE nombre = 'Franquicia Ejemplo 2' LIMIT 1));

-- Datos iniciales para productos (usando subconsultas para obtener los IDs de sucursales)
INSERT INTO productos (nombre, stock, sucursal_id) VALUES 
('Producto A', 100, (SELECT id FROM sucursales WHERE nombre = 'Sucursal Central' LIMIT 1)),
('Producto B', 50, (SELECT id FROM sucursales WHERE nombre = 'Sucursal Central' LIMIT 1)),
('Producto C', 75, (SELECT id FROM sucursales WHERE nombre = 'Sucursal Norte' LIMIT 1)),
('Producto D', 30, (SELECT id FROM sucursales WHERE nombre = 'Sucursal Principal' LIMIT 1));
