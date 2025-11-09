-- Script SQL completo para la base de datos escuela
-- Ejecutar en pgAdmin o psql como usuario postgres

DROP DATABASE IF EXISTS escuela;
CREATE DATABASE escuela;

-- Conectarse a la base de datos escuela antes de crear las tablas
-- En pgAdmin: Click derecho en escuela > Query Tool
-- En psql: \c escuela

-- Descomentar la siguiente línea si estás en psql
-- \c escuela;

DROP TABLE IF EXISTS alumno;

CREATE TABLE alumno (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO alumno (nombre, apellido, email) VALUES ('Juan', 'Perez', 'juan@mail.com');
INSERT INTO alumno (nombre, apellido, email) VALUES ('Maria', 'Lopez', 'maria@mail.com');
INSERT INTO alumno (nombre, apellido, email) VALUES ('Carlos', 'Garcia', 'carlos@mail.com');
INSERT INTO alumno (nombre, apellido, email) VALUES ('Ana', 'Martinez', 'ana@mail.com');
INSERT INTO alumno (nombre, apellido, email) VALUES ('Luis', 'Rodriguez', 'luis@mail.com');
