# Instrucciones de Configuracion - Lab 11

## Pasos Detallados para Ejecutar el Proyecto

### 1. Verificar Requisitos

Antes de ejecutar el proyecto, asegurese de tener instalado:

- **JDK 17** o superior
  ```bash
  java -version
  ```

- **Maven 3.6** o superior
  ```bash
  mvn -version
  ```

### 2. Compilar el Proyecto

Desde el directorio raiz del proyecto (labora11), ejecute:

```bash
mvn clean install
```

Este comando:
- Limpia compilaciones anteriores
- Descarga todas las dependencias
- Compila el proyecto
- Ejecuta las pruebas (si las hay)
- Empaqueta la aplicacion

### 3. Ejecutar la Aplicacion

Hay dos formas de ejecutar la aplicacion:

#### Opcion A: Con Maven
```bash
mvn spring-boot:run
```

#### Opcion B: Con Java
```bash
java -jar target/labora11-0.0.1-SNAPSHOT.jar
```

### 4. Acceder a la Aplicacion

Una vez iniciada la aplicacion, vera en la consola:

```
Started Labora11Application in X.XXX seconds
```

Luego acceda a:
- **URL Principal:** http://localhost:8087
- **H2 Console:** http://localhost:8087/h2-console

### 5. Configurar H2 Console

En la consola de H2, use estos parametros:

```
JDBC URL: jdbc:h2:mem:escuela
User Name: tecsup
Password: (dejar vacio)
```

### 6. Iniciar Sesion

Use uno de estos usuarios:

**Administrador (acceso completo):**
- Usuario: `admin`
- Contraseña: `12345`

**Usuario regular (solo lectura):**
- Usuario: `user`
- Contraseña: `12345`

### 7. Probar Funcionalidades

#### Como ADMIN:
1. Ir a http://localhost:8087/login
2. Iniciar sesion con admin/12345
3. Ver listado de alumnos
4. Crear un nuevo alumno
5. Editar un alumno existente
6. Eliminar un alumno
7. Exportar a PDF
8. Exportar a Excel

#### Como USER:
1. Ir a http://localhost:8087/login
2. Iniciar sesion con user/12345
3. Ver listado de alumnos (sin opciones de edicion)
4. Intentar acceder a /form (deberia mostrar error 403)

### 8. Cambiar Idioma

Use los botones ES/EN en la barra de navegacion para cambiar entre Español e Ingles.

### 9. Ver Auditoria

Puede consultar la tabla de auditoria en H2 Console:

```sql
SELECT * FROM AUDITORIA ORDER BY FECHA DESC;
```

Esto muestra todas las operaciones realizadas por los usuarios.

### 10. Detener la Aplicacion

Presione `Ctrl + C` en la terminal donde se ejecuta la aplicacion.

## Solución de Problemas

### Puerto 8087 ya en uso
Si el puerto 8087 esta ocupado, puede cambiarlo en:
```
src/main/resources/application.properties
```
Modificar: `server.port=8087` a otro puerto disponible.

### Error de compilacion
Asegurese de:
- Tener JDK 17 (no JRE)
- Tener conexion a internet para descargar dependencias
- Ejecutar `mvn clean` antes de compilar nuevamente

### Base de datos no inicializa
Verifique que el archivo `import.sql` este en:
```
src/main/resources/import.sql
```

### Login no funciona
Las contraseñas en `import.sql` estan encriptadas con BCrypt.
El hash corresponde a la contraseña: `12345`

Si necesita generar un nuevo hash:
```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hash = encoder.encode("suPassword");
System.out.println(hash);
```

## Estructura de la Base de Datos

### Tabla: ALUMNOS
```sql
CREATE TABLE alumnos (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    creditos INTEGER NOT NULL
);
```

### Tabla: USUARIOS
```sql
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    enabled BOOLEAN NOT NULL
);
```

### Tabla: ROLES
```sql
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30) UNIQUE NOT NULL
);
```

### Tabla: USUARIOS_ROLES
```sql
CREATE TABLE usuarios_roles (
    usuario_id BIGINT,
    rol_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);
```

### Tabla: AUDITORIA
```sql
CREATE TABLE auditoria (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_metodo VARCHAR(255),
    usuario VARCHAR(255),
    fecha TIMESTAMP
);
```

## Notas Importantes

1. **H2 es base de datos en memoria**: Los datos se pierden al detener la aplicacion
2. **CSRF esta deshabilitado**: Solo para facilitar el acceso a H2 Console
3. **El proyecto usa Spring Boot 3.0.1**: Compatible con Jakarta EE (no javax)
4. **Las contraseñas deben estar encriptadas**: Usar BCryptPasswordEncoder
5. **Los reportes se generan on-demand**: No se almacenan en el servidor

## Recursos Adicionales

- Documentacion Spring Security: https://spring.io/projects/spring-security
- Documentacion Spring Boot: https://spring.io/projects/spring-boot
- Documentacion Thymeleaf: https://www.thymeleaf.org/
- Documentacion H2: http://www.h2database.com/
