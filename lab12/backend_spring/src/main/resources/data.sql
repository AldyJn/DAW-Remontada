INSERT INTO categoria (id, nombre, descripcion) VALUES (1, 'Electrónica', 'Productos electrónicos y tecnológicos') ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, nombre, descripcion) VALUES (2, 'Ropa', 'Vestimenta y accesorios') ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, nombre, descripcion) VALUES (3, 'Alimentos', 'Productos alimenticios') ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, nombre, descripcion) VALUES (4, 'Hogar', 'Artículos para el hogar') ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, nombre, descripcion) VALUES (5, 'Deportes', 'Equipamiento deportivo') ON CONFLICT (id) DO NOTHING;

INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (1, 'Laptop HP', 2500.00, 15, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (2, 'Mouse Logitech', 45.50, 50, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (3, 'Teclado Mecánico', 120.00, 30, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (4, 'Camisa Polo', 65.00, 40, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (5, 'Pantalón Jean', 85.00, 25, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (6, 'Zapatillas Nike', 150.00, 20, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (7, 'Arroz 5kg', 18.50, 100, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (8, 'Aceite Vegetal', 12.00, 80, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (9, 'Licuadora Oster', 180.00, 12, 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (10, 'Sartén Antiadherente', 55.00, 35, 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (11, 'Balón de Fútbol', 45.00, 25, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, nombre, precio, stock, categoria_id) VALUES (12, 'Raqueta de Tenis', 200.00, 10, 5) ON CONFLICT (id) DO NOTHING;

SELECT setval('categoria_id_seq', (SELECT MAX(id) FROM categoria));
SELECT setval('producto_id_seq', (SELECT MAX(id) FROM producto));
