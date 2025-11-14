
-- SCRIPT DE BASE DE DATOS
-- Sistema de Gestión de Restaurante "Sabor Gourmet"

-- SECCIÓN 1: ELIMINAR TABLAS EXISTENTES

DROP TABLE IF EXISTS bitacora CASCADE;
DROP TABLE IF EXISTS detalle_factura CASCADE;
DROP TABLE IF EXISTS detalle_compra CASCADE;
DROP TABLE IF EXISTS detalle_pedido CASCADE;
DROP TABLE IF EXISTS plato_insumo CASCADE;
DROP TABLE IF EXISTS facturas CASCADE;
DROP TABLE IF EXISTS compras CASCADE;
DROP TABLE IF EXISTS pedidos CASCADE;
DROP TABLE IF EXISTS usuario_rol CASCADE;
DROP TABLE IF EXISTS mesas CASCADE;
DROP TABLE IF EXISTS platos CASCADE;
DROP TABLE IF EXISTS insumos CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;
DROP TABLE IF EXISTS proveedores CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

-- SECCIÓN 2: CREAR TABLAS

-- Tabla: roles
CREATE TABLE roles (
    id_rol BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla: usuarios
CREATE TABLE usuarios (
    id_usuario BIGSERIAL PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT true
);

-- Tabla: usuario_rol (relación muchos a muchos)
CREATE TABLE usuario_rol (
    id_usuario BIGINT NOT NULL,
    id_rol BIGINT NOT NULL,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol) ON DELETE CASCADE
);

-- Tabla: mesas
CREATE TABLE mesas (
    id_mesa BIGSERIAL PRIMARY KEY,
    numero INTEGER NOT NULL UNIQUE,
    capacidad INTEGER NOT NULL CHECK (capacidad >= 1),
    estado VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE'
);

-- Tabla: platos
CREATE TABLE platos (
    id_plato BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL CHECK (precio > 0),
    descripcion VARCHAR(500),
    estado BOOLEAN NOT NULL DEFAULT true
);

-- Tabla: insumos
CREATE TABLE insumos (
    id_insumo BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    unidad_medida VARCHAR(20) NOT NULL,
    stock DECIMAL(10, 2) NOT NULL CHECK (stock >= 0),
    stock_minimo DECIMAL(10, 2) NOT NULL CHECK (stock_minimo >= 0),
    precio_compra DECIMAL(10, 2) NOT NULL CHECK (precio_compra >= 0),
    estado BOOLEAN NOT NULL DEFAULT true
);

-- Tabla: plato_insumo (relación muchos a muchos con atributos)
CREATE TABLE plato_insumo (
    id_plato_insumo BIGSERIAL PRIMARY KEY,
    id_plato BIGINT NOT NULL,
    id_insumo BIGINT NOT NULL,
    cantidad_usada DECIMAL(10, 2) NOT NULL CHECK (cantidad_usada > 0),
    FOREIGN KEY (id_plato) REFERENCES platos(id_plato) ON DELETE CASCADE,
    FOREIGN KEY (id_insumo) REFERENCES insumos(id_insumo) ON DELETE CASCADE
);

-- Tabla: clientes
CREATE TABLE clientes (
    id_cliente BIGSERIAL PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    estado BOOLEAN NOT NULL DEFAULT true
);

-- Tabla: pedidos
CREATE TABLE pedidos (
    id_pedido BIGSERIAL PRIMARY KEY,
    id_cliente BIGINT,
    id_mesa BIGINT NOT NULL,
    fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE SET NULL,
    FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa) ON DELETE RESTRICT
);

-- Tabla: detalle_pedido
CREATE TABLE detalle_pedido (
    id_detalle_pedido BIGSERIAL PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_plato BIGINT NOT NULL,
    cantidad INTEGER NOT NULL CHECK (cantidad >= 1),
    subtotal DECIMAL(10, 2) NOT NULL CHECK (subtotal > 0),
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_plato) REFERENCES platos(id_plato) ON DELETE RESTRICT
);

-- Tabla: facturas
CREATE TABLE facturas (
    id_factura BIGSERIAL PRIMARY KEY,
    id_pedido BIGINT NOT NULL UNIQUE,
    fecha_emision TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL CHECK (total > 0),
    metodo_pago VARCHAR(20) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT true,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido) ON DELETE RESTRICT
);

-- Tabla: detalle_factura
CREATE TABLE detalle_factura (
    id_detalle_factura BIGSERIAL PRIMARY KEY,
    id_factura BIGINT NOT NULL,
    concepto VARCHAR(200) NOT NULL,
    monto DECIMAL(10, 2) NOT NULL CHECK (monto > 0),
    FOREIGN KEY (id_factura) REFERENCES facturas(id_factura) ON DELETE CASCADE
);

-- Tabla: proveedores
CREATE TABLE proveedores (
    id_proveedor BIGSERIAL PRIMARY KEY,
    ruc VARCHAR(11) NOT NULL UNIQUE,
    nombre VARCHAR(200) NOT NULL,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    direccion VARCHAR(300)
);

-- Tabla: compras
CREATE TABLE compras (
    id_compra BIGSERIAL PRIMARY KEY,
    id_proveedor BIGINT NOT NULL,
    fecha_compra TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL CHECK (total > 0),
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id_proveedor) ON DELETE RESTRICT
);

-- Tabla: detalle_compra
CREATE TABLE detalle_compra (
    id_detalle_compra BIGSERIAL PRIMARY KEY,
    id_compra BIGINT NOT NULL,
    id_insumo BIGINT NOT NULL,
    cantidad DECIMAL(10, 2) NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL CHECK (precio_unitario > 0),
    subtotal DECIMAL(10, 2) NOT NULL CHECK (subtotal > 0),
    FOREIGN KEY (id_compra) REFERENCES compras(id_compra) ON DELETE CASCADE,
    FOREIGN KEY (id_insumo) REFERENCES insumos(id_insumo) ON DELETE RESTRICT
);

-- Tabla: bitacora
CREATE TABLE bitacora (
    id_bitacora BIGSERIAL PRIMARY KEY,
    usuario VARCHAR(100) NOT NULL,
    accion VARCHAR(50) NOT NULL,
    metodo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ip_cliente VARCHAR(45)
);

-- SECCIÓN 3: DATOS INICIALES

-- ROLES
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO roles (nombre) VALUES ('ROLE_MOZO') ON CONFLICT DO NOTHING;
INSERT INTO roles (nombre) VALUES ('ROLE_COCINERO') ON CONFLICT DO NOTHING;
INSERT INTO roles (nombre) VALUES ('ROLE_CAJERO') ON CONFLICT DO NOTHING;

-- USUARIOS (contraseña por defecto: "password123")
-- Hash BCrypt para "password123": $2a$10$XPTSPk7VJKtXKHKPgJZPWulVFyVLKKkGHkPb6yjvPdWvO5LMiKhxi

-- Admin: admin / password123
INSERT INTO usuarios (nombre_usuario, contrasena, estado)
VALUES ('admin', '$2a$10$XPTSPk7VJKtXKHKPgJZPWulVFyVLKKkGHkPb6yjvPdWvO5LMiKhxi', true)
ON CONFLICT DO NOTHING;

-- Mozo: mozo1 / password123
INSERT INTO usuarios (nombre_usuario, contrasena, estado)
VALUES ('mozo1', '$2a$10$XPTSPk7VJKtXKHKPgJZPWulVFyVLKKkGHkPb6yjvPdWvO5LMiKhxi', true)
ON CONFLICT DO NOTHING;

-- Cocinero: cocinero1 / password123
INSERT INTO usuarios (nombre_usuario, contrasena, estado)
VALUES ('cocinero1', '$2a$10$XPTSPk7VJKtXKHKPgJZPWulVFyVLKKkGHkPb6yjvPdWvO5LMiKhxi', true)
ON CONFLICT DO NOTHING;

-- Cajero: cajero1 / password123
INSERT INTO usuarios (nombre_usuario, contrasena, estado)
VALUES ('cajero1', '$2a$10$XPTSPk7VJKtXKHKPgJZPWulVFyVLKKkGHkPb6yjvPdWvO5LMiKhxi', true)
ON CONFLICT DO NOTHING;

-- ASIGNACIÓN DE ROLES A USUARIOS
INSERT INTO usuario_rol (id_usuario, id_rol)
SELECT u.id_usuario, r.id_rol
FROM usuarios u, roles r
WHERE u.nombre_usuario = 'admin' AND r.nombre = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO usuario_rol (id_usuario, id_rol)
SELECT u.id_usuario, r.id_rol
FROM usuarios u, roles r
WHERE u.nombre_usuario = 'mozo1' AND r.nombre = 'ROLE_MOZO'
ON CONFLICT DO NOTHING;

INSERT INTO usuario_rol (id_usuario, id_rol)
SELECT u.id_usuario, r.id_rol
FROM usuarios u, roles r
WHERE u.nombre_usuario = 'cocinero1' AND r.nombre = 'ROLE_COCINERO'
ON CONFLICT DO NOTHING;

INSERT INTO usuario_rol (id_usuario, id_rol)
SELECT u.id_usuario, r.id_rol
FROM usuarios u, roles r
WHERE u.nombre_usuario = 'cajero1' AND r.nombre = 'ROLE_CAJERO'
ON CONFLICT DO NOTHING;

-- MESAS
INSERT INTO mesas (numero, capacidad, estado) VALUES (1, 2, 'DISPONIBLE');
INSERT INTO mesas (numero, capacidad, estado) VALUES (2, 2, 'DISPONIBLE');
INSERT INTO mesas (numero, capacidad, estado) VALUES (3, 4, 'DISPONIBLE');
INSERT INTO mesas (numero, capacidad, estado) VALUES (4, 4, 'DISPONIBLE');
INSERT INTO mesas (numero, capacidad, estado) VALUES (5, 6, 'DISPONIBLE');
INSERT INTO mesas (numero, capacidad, estado) VALUES (6, 6, 'DISPONIBLE');
INSERT INTO mesas (numero, capacidad, estado) VALUES (7, 8, 'DISPONIBLE');
INSERT INTO mesas (numero, capacidad, estado) VALUES (8, 4, 'DISPONIBLE');
INSERT INTO mesas (numero, capacidad, estado) VALUES (9, 2, 'DISPONIBLE');
INSERT INTO mesas (numero, capacidad, estado) VALUES (10, 4, 'DISPONIBLE');

-- PLATOS - ENTRADAS
INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Ensalada César', 'ENTRADA', 18.00, 'Lechuga fresca, pollo grillado, crutones y aderezo césar', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Tequeños', 'ENTRADA', 15.00, '6 unidades de tequeños rellenos de queso', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Causa Limeña', 'ENTRADA', 20.00, 'Causa rellena de pollo o atún', true);

-- PLATOS - FONDOS
INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Lomo Saltado', 'FONDO', 35.00, 'Lomo fino salteado con cebolla, tomate y papas fritas', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Arroz con Pollo', 'FONDO', 28.00, 'Arroz con pollo, papa a la huancaína y ensalada', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Tallarines a lo Alfredo', 'FONDO', 30.00, 'Tallarines en salsa alfredo con pollo o carne', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Ceviche Mixto', 'FONDO', 38.00, 'Pescado, camarones y pulpo en leche de tigre', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Ají de Gallina', 'FONDO', 32.00, 'Pollo deshilachado en salsa de ají amarillo', true);

-- PLATOS - POSTRES
INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Suspiro Limeño', 'POSTRE', 12.00, 'Postre tradicional peruano con manjar blanco', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Tres Leches', 'POSTRE', 14.00, 'Torta bañada en tres leches', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Tiramisu', 'POSTRE', 16.00, 'Postre italiano con café y mascarpone', true);

-- BEBIDAS
INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Chicha Morada', 'BEBIDA', 8.00, 'Bebida tradicional peruana - Jarra 1L', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Limonada', 'BEBIDA', 7.00, 'Limonada natural - Jarra 1L', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Inca Kola', 'BEBIDA', 5.00, 'Gaseosa 500ml', true);

INSERT INTO platos (nombre, tipo, precio, descripcion, estado)
VALUES ('Agua Mineral', 'BEBIDA', 4.00, 'Agua San Luis 625ml', true);

-- INSUMOS
INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Arroz Blanco', 'kg', 100.00, 20.00, 3.50, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Pollo Entero', 'kg', 50.00, 10.00, 12.00, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Lomo de Res', 'kg', 30.00, 5.00, 28.00, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Fideos Tallarín', 'kg', 40.00, 10.00, 4.50, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Papa Blanca', 'kg', 80.00, 15.00, 2.00, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Cebolla Roja', 'kg', 30.00, 10.00, 2.50, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Tomate', 'kg', 25.00, 8.00, 3.00, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Lechuga', 'unidad', 20.00, 5.00, 1.50, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Queso Fresco', 'kg', 15.00, 5.00, 18.00, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Leche Evaporada', 'lata', 30.00, 10.00, 4.20, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Aceite Vegetal', 'litro', 20.00, 5.00, 8.50, true);

INSERT INTO insumos (nombre, unidad_medida, stock, stock_minimo, precio_compra, estado)
VALUES ('Sal', 'kg', 25.00, 5.00, 1.00, true);

-- CLIENTES DE PRUEBA
INSERT INTO clientes (dni, nombres, apellidos, telefono, correo, estado)
VALUES ('72345678', 'Juan Carlos', 'Pérez García', '987654321', 'jperez@email.com', true);

INSERT INTO clientes (dni, nombres, apellidos, telefono, correo, estado)
VALUES ('71234567', 'María Elena', 'Rodríguez López', '976543210', 'mrodriguez@email.com', true);

INSERT INTO clientes (dni, nombres, apellidos, telefono, correo, estado)
VALUES ('73456789', 'Pedro Luis', 'González Torres', '965432109', 'pgonzalez@email.com', true);

INSERT INTO clientes (dni, nombres, apellidos, telefono, correo, estado)
VALUES ('74567890', 'Ana Sofía', 'Martínez Flores', '954321098', 'amartinez@email.com', true);

INSERT INTO clientes (dni, nombres, apellidos, telefono, correo, estado)
VALUES ('75678901', 'Carlos Alberto', 'Sánchez Quispe', '943210987', 'csanchez@email.com', true);

-- PROVEEDORES
INSERT INTO proveedores (ruc, nombre, telefono, correo, direccion)
VALUES ('20123456789', 'Distribuidora de Alimentos SAC', '014567890', 'ventas@alimentos.com', 'Av. Los Alimentos 123, Lima');

INSERT INTO proveedores (ruc, nombre, telefono, correo, direccion)
VALUES ('20234567890', 'Carnes y Aves del Peru EIRL', '014567891', 'ventas@carnesyaves.com', 'Jr. Las Carnes 456, Lima');

INSERT INTO proveedores (ruc, nombre, telefono, correo, direccion)
VALUES ('20345678901', 'Verduras Frescas SAC', '014567892', 'ventas@verduras.com', 'Av. Agricultura 789, Lima');
