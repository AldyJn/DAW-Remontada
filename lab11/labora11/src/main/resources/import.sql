INSERT INTO alumnos (nombre, creditos) VALUES ('Programmer', 5);
INSERT INTO alumnos (nombre, creditos) VALUES ('Developer', 5);
INSERT INTO alumnos (nombre, creditos) VALUES ('Expert', 5);

-- Usuarios con contraseña: 12345 (encriptada con BCrypt)
INSERT INTO usuarios (username, password, enabled) VALUES ('admin', '$2a$10$XURPShQNCsLjp1ESc2laoObo6NRVa/8rJvpWO/WzQX1x1.KNtCKKO', 1);
INSERT INTO usuarios (username, password, enabled) VALUES ('user', '$2a$10$XURPShQNCsLjp1ESc2laoObo6NRVa/8rJvpWO/WzQX1x1.KNtCKKO', 1);

INSERT INTO roles (nombre, usuario_id) VALUES ('ROLE_USER', 1);
INSERT INTO roles (nombre, usuario_id) VALUES ('ROLE_ADMIN', 1);
INSERT INTO roles (nombre, usuario_id) VALUES ('ROLE_USER', 2);
