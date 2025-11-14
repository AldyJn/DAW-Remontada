# Lab 11 - Spring Boot con Spring Security

Aplicacion Spring Boot completa para gestion de Alumnos (CRUD) con Spring Security, autenticacion de usuarios, control de acceso basado en roles, sistema de login personalizado, AOP y generacion de reportes.

## Configuracion del Proyecto

- **Group ID:** com.lab11
- **Artifact ID:** labora11
- **Version:** 0.0.1-SNAPSHOT
- **Java Version:** 17
- **Spring Boot Version:** 3.0.1
- **Puerto:** 8087
- **Base de Datos:** H2 (en memoria)
- **Nombre BD:** escuela

## Caracteristicas

- CRUD completo de Alumnos con JPA/Hibernate
- Spring Security con autenticacion basada en base de datos
- BCrypt para encriptacion de contraseñas
- Control de acceso basado en roles (ROLE_USER y ROLE_ADMIN)
- Login personalizado con mensajes flash
- Internacionalizacion (Español e Ingles)
- AOP para auditoria de operaciones
- Generacion de reportes en PDF y Excel
- Thymeleaf con Spring Security extras
- H2 Console habilitada

## Tecnologias Utilizadas

- Spring Boot 3.0.1
- Spring Security 6
- Spring Data JPA
- Thymeleaf
- Thymeleaf Spring Security Extras
- H2 Database
- AspectJ (AOP)
- iText (PDF)
- Apache POI (Excel)
- Bootstrap 4
- Maven

## Estructura del Proyecto

```
src/main/java/com/lab11/
├── domain/
│   ├── entities/
│   │   ├── Alumno.java
│   │   ├── Auditoria.java
│   │   ├── Usuario.java
│   │   └── Rol.java
│   └── persistence/
│       ├── AlumnoDao.java
│       ├── AuditoriaDao.java
│       └── IUsuarioDao.java
├── services/
│   ├── AlumnoService.java
│   ├── AlumnoServiceImpl.java
│   └── JpaUserDetailsService.java
├── controllers/
│   ├── AlumnoController.java
│   └── LocaleController.java
├── handlers/
│   └── LoginSuccessHandler.java
├── aop/
│   └── LogginAspecto.java
├── views/
│   ├── AlumnoPdfView.java
│   └── AlumnoXlsView.java
├── SpringSecurityConfig.java
├── MvcConfig.java
└── Labora11Application.java
```

## Instalacion y Ejecucion

### Requisitos Previos

- JDK 17 o superior
- Maven 3.6 o superior

### Pasos para Ejecutar

1. Clonar o descargar el proyecto

2. Navegar al directorio del proyecto:
   ```bash
   cd labora11
   ```

3. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

4. Ejecutar la aplicacion:
   ```bash
   mvn spring-boot:run
   ```

5. Acceder a la aplicacion:
   ```
   http://localhost:8087
   ```

6. Acceder a H2 Console:
   ```
   http://localhost:8087/h2-console

   JDBC URL: jdbc:h2:mem:escuela
   Username: tecsup
   Password: (dejar vacio)
   ```

## Usuarios de Prueba

La aplicacion viene con dos usuarios preconfigurados:

| Usuario | Contraseña | Roles |
|---------|-----------|-------|
| admin   | 12345     | ROLE_USER, ROLE_ADMIN |
| user    | 12345     | ROLE_USER |

## Funcionalidades por Rol

### ROLE_USER
- Ver listado de alumnos
- Ver detalles de un alumno
- Cambiar idioma

### ROLE_ADMIN
- Todas las funcionalidades de ROLE_USER
- Crear nuevos alumnos
- Editar alumnos existentes
- Eliminar alumnos
- Exportar reportes (PDF y Excel)

## Endpoints Principales

- `/` - Pagina de inicio
- `/login` - Formulario de login
- `/logout` - Cerrar sesion
- `/listar` - Listado de alumnos
- `/form` - Formulario para crear alumno
- `/form/{id}` - Formulario para editar alumno
- `/eliminar/{id}` - Eliminar alumno
- `/ver/{id}` - Ver detalles de alumno
- `/listar?format=pdf` - Exportar a PDF
- `/listar?format=xls` - Exportar a Excel
- `/h2-console` - Consola de H2
- `/locale?lang=es_ES` - Cambiar a Español
- `/locale?lang=en_US` - Cambiar a Ingles

## Seguridad

La aplicacion implementa las siguientes medidas de seguridad:

- Autenticacion basada en formulario
- Contraseñas encriptadas con BCrypt
- Control de acceso basado en roles con anotaciones @Secured
- Proteccion CSRF (deshabilitada para H2 Console)
- Sesiones seguras
- Paginas de error personalizadas (403)

## AOP y Auditoria

El sistema registra automaticamente todas las operaciones realizadas en el servicio AlumnoServiceImpl:

- Nombre del metodo ejecutado
- Usuario que realizo la operacion
- Fecha y hora de la operacion

La informacion de auditoria se almacena en la tabla `auditoria` de la base de datos.

## Internacionalizacion

La aplicacion soporta dos idiomas:

- Español (es_ES)
- Ingles (en_US)

Los archivos de mensajes se encuentran en:
- `src/main/resources/messages_es.properties`
- `src/main/resources/messages_en.properties`

## Reportes

### PDF
Utiliza iText para generar reportes en formato PDF con:
- Tabla de alumnos
- Encabezados con formato
- Colores personalizados

### Excel
Utiliza Apache POI para generar reportes en formato Excel con:
- Hoja de calculo con datos de alumnos
- Encabezados con estilo
- Auto-ajuste de columnas

## Autor

Este proyecto fue desarrollado como parte del Laboratorio 11 del curso de Desarrollo de Aplicaciones Web.

Basado en los materiales del profesor Ricardo Coello Palomino - CJAVA 2024

## Licencia

Este proyecto es para fines educativos.
