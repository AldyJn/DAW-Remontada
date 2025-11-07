-- ============================================
-- Script de Base de Datos para PostgreSQL
-- Sistema de Gestión Académica
-- ============================================

-- Eliminar base de datos si existe y crear una nueva
DROP DATABASE IF EXISTS escuela;
CREATE DATABASE escuela;

-- Conectar a la base de datos
\c escuela;

-- ============================================
-- CREACIÓN DE TABLAS
-- ============================================

-- Tabla: Administrador
CREATE TABLE administrador (
    codigo VARCHAR(10) PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    clave VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    estado INTEGER DEFAULT 1
);

-- Tabla: Alumno
CREATE TABLE alumno (
    codigo VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    estado INTEGER DEFAULT 1
);

-- Tabla: Curso
CREATE TABLE curso (
    codigo VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    creditos INTEGER NOT NULL,
    estado INTEGER DEFAULT 1
);

-- Tabla: Periodo Académico
CREATE TABLE periodo_academico (
    id_periodo SERIAL PRIMARY KEY,
    nombre_periodo VARCHAR(100) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado INTEGER DEFAULT 1
);

-- Tabla: Matrícula
CREATE TABLE matricula (
    id_matricula SERIAL PRIMARY KEY,
    id_alumno VARCHAR(10) NOT NULL,
    id_periodo INTEGER NOT NULL,
    fecha_matricula DATE NOT NULL,
    estado INTEGER DEFAULT 1,
    FOREIGN KEY (id_alumno) REFERENCES alumno(codigo)
);

-- Tabla: Detalle de Matrícula
CREATE TABLE detalle_matricula (
    id_detalle SERIAL PRIMARY KEY,
    id_matricula INTEGER NOT NULL,
    id_curso VARCHAR(10) NOT NULL,
    estado INTEGER DEFAULT 1,
    FOREIGN KEY (id_matricula) REFERENCES matricula(id_matricula),
    FOREIGN KEY (id_curso) REFERENCES curso(codigo)
);

-- Tabla: Evaluación
CREATE TABLE evaluacion (
    id_evaluacion SERIAL PRIMARY KEY,
    id_curso VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    peso DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (id_curso) REFERENCES curso(codigo)
);

-- Tabla: Nota
CREATE TABLE nota (
    id_nota SERIAL PRIMARY KEY,
    id_detalle INTEGER NOT NULL,
    id_evaluacion INTEGER NOT NULL,
    nota DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (id_detalle) REFERENCES detalle_matricula(id_detalle),
    FOREIGN KEY (id_evaluacion) REFERENCES evaluacion(id_evaluacion)
);

-- Tabla: Sesión de Clase
CREATE TABLE sesion_clase (
    id_sesion SERIAL PRIMARY KEY,
    id_curso VARCHAR(10) NOT NULL,
    id_periodo INTEGER NOT NULL,
    semana INTEGER NOT NULL,
    fecha DATE NOT NULL,
    tema VARCHAR(200) NOT NULL,
    FOREIGN KEY (id_curso) REFERENCES curso(codigo),
    FOREIGN KEY (id_periodo) REFERENCES periodo_academico(id_periodo)
);

-- Tabla: Asistencia
CREATE TABLE asistencia (
    id_asistencia SERIAL PRIMARY KEY,
    id_sesion INTEGER NOT NULL,
    id_detalle INTEGER NOT NULL,
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_sesion) REFERENCES sesion_clase(id_sesion),
    FOREIGN KEY (id_detalle) REFERENCES detalle_matricula(id_detalle)
);

-- ============================================
-- STORED PROCEDURES - ALUMNO
-- ============================================

-- Procedimiento: Insertar Alumno
CREATE OR REPLACE FUNCTION sp_insertar_alumno(
    p_codigo VARCHAR(10),
    p_nombre VARCHAR(100),
    p_apellido VARCHAR(100),
    p_correo VARCHAR(100)
)
RETURNS VOID AS $$
BEGIN
    INSERT INTO alumno (codigo, nombre, apellido, correo, estado)
    VALUES (p_codigo, p_nombre, p_apellido, p_correo, 1);
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Actualizar Alumno
CREATE OR REPLACE FUNCTION sp_actualizar_alumno(
    p_codigo VARCHAR(10),
    p_nombre VARCHAR(100),
    p_apellido VARCHAR(100),
    p_correo VARCHAR(100)
)
RETURNS VOID AS $$
BEGIN
    UPDATE alumno
    SET nombre = p_nombre,
        apellido = p_apellido,
        correo = p_correo
    WHERE codigo = p_codigo;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Eliminar Alumno (lógico)
CREATE OR REPLACE FUNCTION sp_eliminar_alumno(p_codigo VARCHAR(10))
RETURNS VOID AS $$
BEGIN
    UPDATE alumno SET estado = 0 WHERE codigo = p_codigo;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Buscar Alumno
CREATE OR REPLACE FUNCTION sp_buscar_alumno(p_codigo VARCHAR(10))
RETURNS TABLE(
    codigo VARCHAR(10),
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    correo VARCHAR(100),
    estado INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT a.codigo, a.nombre, a.apellido, a.correo, a.estado
    FROM alumno a
    WHERE a.codigo = p_codigo AND a.estado = 1;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Listar Alumnos
CREATE OR REPLACE FUNCTION sp_listar_alumnos()
RETURNS TABLE(
    codigo VARCHAR(10),
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    correo VARCHAR(100),
    estado INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT a.codigo, a.nombre, a.apellido, a.correo, a.estado
    FROM alumno a
    WHERE a.estado = 1
    ORDER BY a.apellido, a.nombre;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- STORED PROCEDURES - CURSO
-- ============================================

-- Procedimiento: Insertar Curso
CREATE OR REPLACE FUNCTION sp_insertar_curso(
    p_codigo VARCHAR(10),
    p_nombre VARCHAR(100),
    p_creditos INTEGER
)
RETURNS VOID AS $$
BEGIN
    INSERT INTO curso (codigo, nombre, creditos, estado)
    VALUES (p_codigo, p_nombre, p_creditos, 1);
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Actualizar Curso
CREATE OR REPLACE FUNCTION sp_actualizar_curso(
    p_codigo VARCHAR(10),
    p_nombre VARCHAR(100),
    p_creditos INTEGER
)
RETURNS VOID AS $$
BEGIN
    UPDATE curso
    SET nombre = p_nombre,
        creditos = p_creditos
    WHERE codigo = p_codigo;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Eliminar Curso (lógico)
CREATE OR REPLACE FUNCTION sp_eliminar_curso(p_codigo VARCHAR(10))
RETURNS VOID AS $$
BEGIN
    UPDATE curso SET estado = 0 WHERE codigo = p_codigo;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Buscar Curso
CREATE OR REPLACE FUNCTION sp_buscar_curso(p_codigo VARCHAR(10))
RETURNS TABLE(
    codigo VARCHAR(10),
    nombre VARCHAR(100),
    creditos INTEGER,
    estado INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT c.codigo, c.nombre, c.creditos, c.estado
    FROM curso c
    WHERE c.codigo = p_codigo AND c.estado = 1;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Listar Cursos
CREATE OR REPLACE FUNCTION sp_listar_cursos()
RETURNS TABLE(
    codigo VARCHAR(10),
    nombre VARCHAR(100),
    creditos INTEGER,
    estado INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT c.codigo, c.nombre, c.creditos, c.estado
    FROM curso c
    WHERE c.estado = 1
    ORDER BY c.nombre;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Buscar Cursos por Nombre
CREATE OR REPLACE FUNCTION sp_buscar_cursos_por_nombre(p_nombre VARCHAR(100))
RETURNS TABLE(
    codigo VARCHAR(10),
    nombre VARCHAR(100),
    creditos INTEGER,
    estado INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT c.codigo, c.nombre, c.creditos, c.estado
    FROM curso c
    WHERE c.nombre ILIKE '%' || p_nombre || '%' AND c.estado = 1
    ORDER BY c.nombre;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Listar Cursos por Créditos
CREATE OR REPLACE FUNCTION sp_listar_cursos_por_creditos(p_creditos INTEGER)
RETURNS TABLE(
    codigo VARCHAR(10),
    nombre VARCHAR(100),
    creditos INTEGER,
    estado INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT c.codigo, c.nombre, c.creditos, c.estado
    FROM curso c
    WHERE c.creditos = p_creditos AND c.estado = 1
    ORDER BY c.nombre;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- STORED PROCEDURES - ADMINISTRADOR
-- ============================================

-- Procedimiento: Insertar Administrador
CREATE OR REPLACE FUNCTION sp_insertar_administrador(
    p_codigo VARCHAR(10),
    p_usuario VARCHAR(50),
    p_clave VARCHAR(100),
    p_nombre VARCHAR(100)
)
RETURNS VOID AS $$
BEGIN
    INSERT INTO administrador (codigo, usuario, clave, nombre, estado)
    VALUES (p_codigo, p_usuario, p_clave, p_nombre, 1);
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Actualizar Administrador
CREATE OR REPLACE FUNCTION sp_actualizar_administrador(
    p_codigo VARCHAR(10),
    p_usuario VARCHAR(50),
    p_clave VARCHAR(100),
    p_nombre VARCHAR(100)
)
RETURNS VOID AS $$
BEGIN
    UPDATE administrador
    SET usuario = p_usuario,
        clave = p_clave,
        nombre = p_nombre
    WHERE codigo = p_codigo;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Eliminar Administrador (lógico)
CREATE OR REPLACE FUNCTION sp_eliminar_administrador(p_codigo VARCHAR(10))
RETURNS VOID AS $$
BEGIN
    UPDATE administrador SET estado = 0 WHERE codigo = p_codigo;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Buscar Administrador
CREATE OR REPLACE FUNCTION sp_buscar_administrador(p_codigo VARCHAR(10))
RETURNS TABLE(
    codigo VARCHAR(10),
    usuario VARCHAR(50),
    clave VARCHAR(100),
    nombre VARCHAR(100),
    estado INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT a.codigo, a.usuario, a.clave, a.nombre, a.estado
    FROM administrador a
    WHERE a.codigo = p_codigo AND a.estado = 1;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Listar Administradores
CREATE OR REPLACE FUNCTION sp_listar_administradores()
RETURNS TABLE(
    codigo VARCHAR(10),
    usuario VARCHAR(50),
    clave VARCHAR(100),
    nombre VARCHAR(100),
    estado INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT a.codigo, a.usuario, a.clave, a.nombre, a.estado
    FROM administrador a
    WHERE a.estado = 1
    ORDER BY a.nombre;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- STORED PROCEDURES - MATRÍCULA
-- ============================================

-- Procedimiento: Insertar Matrícula
CREATE OR REPLACE FUNCTION sp_insertar_matricula(
    p_id_alumno VARCHAR(10),
    p_id_periodo INTEGER,
    p_fecha_matricula DATE
)
RETURNS VOID AS $$
BEGIN
    INSERT INTO matricula (id_alumno, id_periodo, fecha_matricula, estado)
    VALUES (p_id_alumno, p_id_periodo, p_fecha_matricula, 1);
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Retirar Matrícula (eliminación lógica)
CREATE OR REPLACE FUNCTION sp_retirar_matricula(p_id_matricula INTEGER)
RETURNS VOID AS $$
BEGIN
    UPDATE matricula SET estado = 0 WHERE id_matricula = p_id_matricula;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Listar Cursos de un Alumno
CREATE OR REPLACE FUNCTION sp_listar_cursos_alumno(p_id_alumno VARCHAR(10))
RETURNS TABLE(
    codigo VARCHAR(10),
    nombre VARCHAR(100),
    creditos INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT DISTINCT c.codigo, c.nombre, c.creditos
    FROM curso c
    INNER JOIN detalle_matricula dm ON c.codigo = dm.id_curso
    INNER JOIN matricula m ON dm.id_matricula = m.id_matricula
    WHERE m.id_alumno = p_id_alumno AND m.estado = 1;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- STORED PROCEDURES - DETALLE MATRÍCULA
-- ============================================

-- Procedimiento: Insertar Detalle Matrícula
CREATE OR REPLACE FUNCTION sp_insertar_detalle_matricula(
    p_id_matricula INTEGER,
    p_id_curso VARCHAR(10)
)
RETURNS VOID AS $$
BEGIN
    INSERT INTO detalle_matricula (id_matricula, id_curso, estado)
    VALUES (p_id_matricula, p_id_curso, 1);
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Anular Detalle Matrícula
CREATE OR REPLACE FUNCTION sp_anular_detalle_matricula(p_id_detalle INTEGER)
RETURNS VOID AS $$
BEGIN
    UPDATE detalle_matricula SET estado = 0 WHERE id_detalle = p_id_detalle;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- STORED PROCEDURES - NOTA
-- ============================================

-- Procedimiento: Registrar Nota
CREATE OR REPLACE FUNCTION sp_registrar_nota(
    p_id_detalle INTEGER,
    p_id_evaluacion INTEGER,
    p_nota DOUBLE PRECISION
)
RETURNS VOID AS $$
BEGIN
    INSERT INTO nota (id_detalle, id_evaluacion, nota)
    VALUES (p_id_detalle, p_id_evaluacion, p_nota);
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Listar Notas de un Alumno
CREATE OR REPLACE FUNCTION sp_listar_notas_alumno(p_id_detalle INTEGER)
RETURNS TABLE(
    id_nota INTEGER,
    id_detalle INTEGER,
    id_evaluacion INTEGER,
    nota DOUBLE PRECISION
) AS $$
BEGIN
    RETURN QUERY
    SELECT n.id_nota, n.id_detalle, n.id_evaluacion, n.nota
    FROM nota n
    WHERE n.id_detalle = p_id_detalle;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- STORED PROCEDURES - ASISTENCIA
-- ============================================

-- Procedimiento: Registrar Asistencia
CREATE OR REPLACE FUNCTION sp_registrar_asistencia(
    p_id_sesion INTEGER,
    p_id_detalle INTEGER,
    p_estado VARCHAR(20)
)
RETURNS VOID AS $$
BEGIN
    INSERT INTO asistencia (id_sesion, id_detalle, estado)
    VALUES (p_id_sesion, p_id_detalle, p_estado);
END;
$$ LANGUAGE plpgsql;

-- Procedimiento: Listar Asistencias de un Alumno
CREATE OR REPLACE FUNCTION sp_listar_asistencias_alumno(p_id_detalle INTEGER)
RETURNS TABLE(
    id_asistencia INTEGER,
    id_sesion INTEGER,
    id_detalle INTEGER,
    estado VARCHAR(20)
) AS $$
BEGIN
    RETURN QUERY
    SELECT a.id_asistencia, a.id_sesion, a.id_detalle, a.estado
    FROM asistencia a
    WHERE a.id_detalle = p_id_detalle;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- VISTAS ÚTILES
-- ============================================

-- Vista: Matrícula con información completa
CREATE OR REPLACE VIEW vista_matriculas_completas AS
SELECT
    dm.id_detalle,
    dm.id_matricula,
    a.codigo as codigo_alumno,
    a.nombre || ' ' || a.apellido as nombre_alumno,
    c.codigo as codigo_curso,
    c.nombre as nombre_curso,
    p.id_periodo,
    p.nombre_periodo,
    m.fecha_matricula
FROM detalle_matricula dm
INNER JOIN matricula m ON dm.id_matricula = m.id_matricula
INNER JOIN alumno a ON m.id_alumno = a.codigo
INNER JOIN curso c ON dm.id_curso = c.codigo
INNER JOIN periodo_academico p ON m.id_periodo = p.id_periodo
WHERE dm.estado = 1 AND m.estado = 1 AND a.estado = 1 AND c.estado = 1;

-- Vista: Evaluaciones con información del curso
CREATE OR REPLACE VIEW vista_evaluaciones_completas AS
SELECT
    e.id_evaluacion,
    e.id_curso,
    c.nombre as nombre_curso,
    e.nombre as nombre_evaluacion,
    e.peso
FROM evaluacion e
INNER JOIN curso c ON e.id_curso = c.codigo
WHERE c.estado = 1;

-- ============================================
-- DATOS DE PRUEBA
-- ============================================

-- Insertar Administradores de prueba
INSERT INTO administrador (codigo, usuario, clave, nombre, estado) VALUES
('ADM001', 'admin', '123456', 'Administrador Principal', 1),
('ADM002', 'supervisor', '123456', 'Supervisor General', 1);

-- Insertar Alumnos de prueba
INSERT INTO alumno (codigo, nombre, apellido, correo, estado) VALUES
('ALU001', 'Juan', 'Pérez García', 'juan.perez@mail.com', 1),
('ALU002', 'María', 'López Martínez', 'maria.lopez@mail.com', 1),
('ALU003', 'Carlos', 'Rodríguez Silva', 'carlos.rodriguez@mail.com', 1),
('ALU004', 'Ana', 'Fernández Torres', 'ana.fernandez@mail.com', 1);

-- Insertar Cursos de prueba
INSERT INTO curso (codigo, nombre, creditos, estado) VALUES
('CUR001', 'Programación I', 4, 1),
('CUR002', 'Base de Datos', 4, 1),
('CUR003', 'Desarrollo Web', 3, 1),
('CUR004', 'Matemática Aplicada', 5, 1),
('CUR005', 'Inglés Técnico', 2, 1);

-- Insertar Periodo Académico de prueba
INSERT INTO periodo_academico (nombre_periodo, fecha_inicio, fecha_fin, estado) VALUES
('Periodo 2025-1', '2025-03-01', '2025-07-31', 1),
('Periodo 2025-2', '2025-08-01', '2025-12-31', 1);

-- Insertar Evaluaciones de prueba
INSERT INTO evaluacion (id_curso, nombre, peso) VALUES
('CUR001', 'Examen Parcial', 0.30),
('CUR001', 'Examen Final', 0.40),
('CUR001', 'Prácticas', 0.30),
('CUR002', 'Examen Parcial', 0.35),
('CUR002', 'Examen Final', 0.35),
('CUR002', 'Proyecto', 0.30),
('CUR003', 'Examen Parcial', 0.25),
('CUR003', 'Examen Final', 0.35),
('CUR003', 'Trabajos', 0.40);

-- Insertar Matrículas de prueba
INSERT INTO matricula (id_alumno, id_periodo, fecha_matricula, estado) VALUES
('ALU001', 1, '2025-02-15', 1),
('ALU002', 1, '2025-02-16', 1),
('ALU003', 1, '2025-02-17', 1);

-- Insertar Detalles de Matrícula (cursos matriculados)
INSERT INTO detalle_matricula (id_matricula, id_curso, estado) VALUES
(1, 'CUR001', 1),  -- Juan Pérez - Programación I
(1, 'CUR002', 1),  -- Juan Pérez - Base de Datos
(1, 'CUR003', 1),  -- Juan Pérez - Desarrollo Web
(2, 'CUR001', 1),  -- María López - Programación I
(2, 'CUR002', 1),  -- María López - Base de Datos
(3, 'CUR002', 1),  -- Carlos Rodríguez - Base de Datos
(3, 'CUR003', 1);  -- Carlos Rodríguez - Desarrollo Web

-- Insertar Notas de ejemplo
-- Notas de Juan Pérez (id_detalle: 1, 2, 3)
INSERT INTO nota (id_detalle, id_evaluacion, nota) VALUES
-- Programación I (id_detalle: 1)
(1, 1, 16.5),  -- Examen Parcial
(1, 2, 17.0),  -- Examen Final
(1, 3, 18.5),  -- Prácticas
-- Base de Datos (id_detalle: 2)
(2, 4, 15.0),  -- Examen Parcial
(2, 5, 16.5),  -- Examen Final
(2, 6, 17.0),  -- Proyecto
-- Desarrollo Web (id_detalle: 3)
(3, 7, 18.0),  -- Examen Parcial
(3, 8, 17.5),  -- Examen Final
(3, 9, 19.0);  -- Trabajos

-- Notas de María López (id_detalle: 4, 5)
INSERT INTO nota (id_detalle, id_evaluacion, nota) VALUES
-- Programación I (id_detalle: 4)
(4, 1, 14.5),  -- Examen Parcial
(4, 2, 15.0),  -- Examen Final
(4, 3, 16.0),  -- Prácticas
-- Base de Datos (id_detalle: 5)
(5, 4, 17.5),  -- Examen Parcial
(5, 5, 18.0),  -- Examen Final
(5, 6, 17.0);  -- Proyecto

-- Notas de Carlos Rodríguez (id_detalle: 6, 7)
INSERT INTO nota (id_detalle, id_evaluacion, nota) VALUES
-- Base de Datos (id_detalle: 6)
(6, 4, 13.0),  -- Examen Parcial
(6, 5, 14.5),  -- Examen Final
-- Desarrollo Web (id_detalle: 7)
(7, 7, 16.0),  -- Examen Parcial
(7, 8, 16.5),  -- Examen Final
(7, 9, 18.0);  -- Trabajos

-- Insertar Sesiones de Clase
INSERT INTO sesion_clase (id_curso, id_periodo, semana, fecha, tema) VALUES
-- Programación I
('CUR001', 1, 1, '2025-03-05', 'Introducción a la Programación'),
('CUR001', 1, 2, '2025-03-08', 'Variables y Tipos de Datos'),
('CUR001', 1, 3, '2025-03-12', 'Estructuras de Control'),
('CUR001', 1, 4, '2025-03-15', 'Funciones y Procedimientos'),
('CUR001', 1, 5, '2025-03-19', 'Arrays y Colecciones'),
-- Base de Datos
('CUR002', 1, 1, '2025-03-06', 'Introducción a Bases de Datos'),
('CUR002', 1, 2, '2025-03-09', 'Modelo Entidad-Relación'),
('CUR002', 1, 3, '2025-03-13', 'SQL Básico - SELECT'),
('CUR002', 1, 4, '2025-03-16', 'SQL - INSERT, UPDATE, DELETE'),
('CUR002', 1, 5, '2025-03-20', 'Joins y Consultas Complejas'),
-- Desarrollo Web
('CUR003', 1, 1, '2025-03-07', 'Introducción a HTML'),
('CUR003', 1, 2, '2025-03-10', 'CSS y Estilos'),
('CUR003', 1, 3, '2025-03-14', 'JavaScript Básico'),
('CUR003', 1, 4, '2025-03-17', 'DOM y Eventos'),
('CUR003', 1, 5, '2025-03-21', 'Formularios y Validación');

-- Insertar Asistencias
-- Sesión 1: Programación I - Introducción (2025-03-05)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(1, 1, 'P'),   -- Juan Pérez - Presente
(1, 4, 'P');   -- María López - Presente

-- Sesión 2: Programación I - Variables (2025-03-08)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(2, 1, 'P'),   -- Juan Pérez - Presente
(2, 4, 'T');   -- María López - Tardanza

-- Sesión 3: Programación I - Estructuras (2025-03-12)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(3, 1, 'P'),   -- Juan Pérez - Presente
(3, 4, 'A');   -- María López - Ausente

-- Sesión 4: Programación I - Funciones (2025-03-15)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(4, 1, 'P'),   -- Juan Pérez - Presente
(4, 4, 'P');   -- María López - Presente

-- Sesión 5: Programación I - Arrays (2025-03-19)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(5, 1, 'P'),   -- Juan Pérez - Presente
(5, 4, 'P');   -- María López - Presente

-- Sesión 6: Base de Datos - Introducción (2025-03-06)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(6, 2, 'P'),   -- Juan Pérez - BD - Presente
(6, 5, 'P'),   -- María López - BD - Presente
(6, 6, 'T');   -- Carlos Rodríguez - BD - Tardanza

-- Sesión 7: Base de Datos - Modelo ER (2025-03-09)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(7, 2, 'P'),   -- Juan Pérez - BD - Presente
(7, 5, 'P'),   -- María López - BD - Presente
(7, 6, 'P');   -- Carlos Rodríguez - BD - Presente

-- Sesión 8: Base de Datos - SQL SELECT (2025-03-13)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(8, 2, 'P'),   -- Juan Pérez - BD - Presente
(8, 5, 'A'),   -- María López - BD - Ausente
(8, 6, 'P');   -- Carlos Rodríguez - BD - Presente

-- Sesión 9: Base de Datos - INSERT, UPDATE, DELETE (2025-03-16)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(9, 2, 'P'),   -- Juan Pérez - BD - Presente
(9, 5, 'P'),   -- María López - BD - Presente
(9, 6, 'P');   -- Carlos Rodríguez - BD - Presente

-- Sesión 10: Base de Datos - Joins (2025-03-20)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(10, 2, 'P'),  -- Juan Pérez - BD - Presente
(10, 5, 'P'),  -- María López - BD - Presente
(10, 6, 'T');  -- Carlos Rodríguez - BD - Tardanza

-- Sesión 11: Desarrollo Web - HTML (2025-03-07)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(11, 3, 'P'),  -- Juan Pérez - Web - Presente
(11, 7, 'P');  -- Carlos Rodríguez - Web - Presente

-- Sesión 12: Desarrollo Web - CSS (2025-03-10)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(12, 3, 'P'),  -- Juan Pérez - Web - Presente
(12, 7, 'P');  -- Carlos Rodríguez - Web - Presente

-- Sesión 13: Desarrollo Web - JavaScript (2025-03-14)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(13, 3, 'T'),  -- Juan Pérez - Web - Tardanza
(13, 7, 'P');  -- Carlos Rodríguez - Web - Presente

-- Sesión 14: Desarrollo Web - DOM (2025-03-17)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(14, 3, 'P'),  -- Juan Pérez - Web - Presente
(14, 7, 'A');  -- Carlos Rodríguez - Web - Ausente

-- Sesión 15: Desarrollo Web - Formularios (2025-03-21)
INSERT INTO asistencia (id_sesion, id_detalle, estado) VALUES
(15, 3, 'P'),  -- Juan Pérez - Web - Presente
(15, 7, 'P');  -- Carlos Rodríguez - Web - Presente

-- ============================================
-- FIN DEL SCRIPT
-- ============================================

COMMIT;
