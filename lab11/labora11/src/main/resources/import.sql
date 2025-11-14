INSERT INTO alumnos (nombre, creditos) VALUES ('Programmer', 5);
INSERT INTO alumnos (nombre, creditos) VALUES ('Developer', 5);
INSERT INTO alumnos (nombre, creditos) VALUES ('Expert', 5);

INSERT INTO usuarios (username, password, enabled) VALUES ('admin', '$2a$10$C2N4Z3n0cITqzXJQgQ.GjO6Bv8JQ.gJ5eULQ4LXLj7Qw5W.BO8B7O', 1);
INSERT INTO usuarios (username, password, enabled) VALUES ('user', '$2a$10$C2N4Z3n0cITqzXJQgQ.GjO6Bv8JQ.gJ5eULQ4LXLj7Qw5W.BO8B7O', 1);

INSERT INTO roles (nombre, usuario_id) VALUES ('ROLE_USER', 1);
INSERT INTO roles (nombre, usuario_id) VALUES ('ROLE_ADMIN', 1);
INSERT INTO roles (nombre, usuario_id) VALUES ('ROLE_USER', 2);
