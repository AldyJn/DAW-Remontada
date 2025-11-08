-- Script de creacion de base de datos y tablas
-- Base de datos: crud_productos_db

-- Eliminar tablas existentes en orden correcto (debido a foreign keys)
DROP TABLE IF EXISTS producto CASCADE;
DROP TABLE IF EXISTS categoria CASCADE;

-- Crear tabla CATEGORIA
CREATE TABLE categoria (
    id_categoria SERIAL PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    CONSTRAINT chk_nombre_categoria CHECK (LENGTH(TRIM(nombre_categoria)) > 0)
);

-- Crear tabla PRODUCTO
CREATE TABLE producto (
    id_producto SERIAL PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio NUMERIC(10, 2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    id_categoria INTEGER NOT NULL,
    CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) 
        REFERENCES categoria(id_categoria) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT chk_nombre_producto CHECK (LENGTH(TRIM(nombre_producto)) > 0),
    CONSTRAINT chk_precio CHECK (precio >= 0),
    CONSTRAINT chk_stock CHECK (stock >= 0)
);

-- Crear indices para mejorar el rendimiento
CREATE INDEX idx_producto_categoria ON producto(id_categoria);
CREATE INDEX idx_categoria_nombre ON categoria(nombre_categoria);
CREATE INDEX idx_producto_nombre ON producto(nombre_producto);

-- Comentarios de documentacion
COMMENT ON TABLE categoria IS 'Tabla que almacena las categorias de productos';
COMMENT ON TABLE producto IS 'Tabla que almacena los productos del sistema';
COMMENT ON COLUMN categoria.id_categoria IS 'Identificador unico de la categoria';
COMMENT ON COLUMN categoria.nombre_categoria IS 'Nombre de la categoria, debe ser unico';
COMMENT ON COLUMN categoria.descripcion IS 'Descripcion detallada de la categoria';
COMMENT ON COLUMN producto.id_producto IS 'Identificador unico del producto';
COMMENT ON COLUMN producto.nombre_producto IS 'Nombre del producto';
COMMENT ON COLUMN producto.descripcion IS 'Descripcion detallada del producto';
COMMENT ON COLUMN producto.precio IS 'Precio del producto en la moneda local';
COMMENT ON COLUMN producto.stock IS 'Cantidad disponible en inventario';
COMMENT ON COLUMN producto.id_categoria IS 'Referencia a la categoria del producto';
