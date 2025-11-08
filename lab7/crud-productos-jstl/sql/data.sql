-- Script de insercion de datos de ejemplo
-- Base de datos: crud_productos_db

-- Insertar categorias de ejemplo
INSERT INTO categoria (nombre_categoria, descripcion) VALUES
('Electronica', 'Dispositivos electronicos y accesorios tecnologicos'),
('Ropa', 'Prendas de vestir y accesorios de moda'),
('Alimentos', 'Productos comestibles y bebidas'),
('Hogar', 'Articulos para el hogar y decoracion'),
('Deportes', 'Equipamiento deportivo y articulos fitness');

-- Insertar productos de ejemplo relacionados con las categorias
INSERT INTO producto (nombre_producto, descripcion, precio, stock, id_categoria) VALUES
-- Productos de Electronica (id_categoria = 1)
('Laptop Dell XPS 13', 'Laptop ultradelgada con procesador Intel i7', 3499.99, 15, 1),
('Mouse Logitech MX Master', 'Mouse inalambrico ergonomico de alta precision', 299.90, 45, 1),
('Teclado Mecanico RGB', 'Teclado mecanico con iluminacion RGB personalizable', 189.50, 30, 1),

-- Productos de Ropa (id_categoria = 2)
('Camisa Casual Azul', 'Camisa de algodon manga larga color azul', 79.90, 50, 2),
('Jeans Clasicos', 'Pantalon jeans corte clasico talla variada', 129.90, 40, 2),

-- Productos de Alimentos (id_categoria = 3)
('Cafe Premium 500g', 'Cafe molido premium de origen colombiano', 45.50, 100, 3),
('Chocolate Artesanal', 'Chocolate amargo 70% cacao artesanal', 18.90, 80, 3),

-- Productos de Hogar (id_categoria = 4)
('Lampara de Mesa LED', 'Lampara de escritorio con luz LED ajustable', 89.90, 25, 4),
('Cojin Decorativo', 'Cojin decorativo de 40x40cm varios colores', 34.90, 60, 4),

-- Productos de Deportes (id_categoria = 5)
('Balon de Futbol', 'Balon profesional tamano oficial', 79.90, 35, 5),
('Pesas Ajustables 10kg', 'Set de pesas ajustables hasta 10kg', 149.90, 20, 5);

-- Verificar los datos insertados
SELECT 'Categorias insertadas:' as mensaje, COUNT(*) as total FROM categoria;
SELECT 'Productos insertados:' as mensaje, COUNT(*) as total FROM producto;
