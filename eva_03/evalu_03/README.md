# Sistema de Gestión de Restaurante "Sabor Gourmet"

Sistema completo de gestión de restaurante desarrollado con Spring Boot 3, Spring Security, JPA/Hibernate y PostgreSQL. Incluye gestión de pedidos, facturación, inventario, clientes y auditoría mediante AOP.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
  - Spring Web (REST API)
  - Spring Data JPA
  - Spring Security
  - Spring AOP
- **PostgreSQL** (Base de datos)
- **JWT** (Autenticación)
- **Lombok** (Reducción de código boilerplate)
- **SpringDoc OpenAPI** (Documentación Swagger)
- **Maven** (Gestión de dependencias)

## Características Principales

### Módulos del Sistema

1. **Gestión de Clientes**
   - Registro y actualización de clientes
   - Consulta por DNI
   - Soft delete

2. **Gestión de Mesas**
   - Control de estado de mesas (Disponible, Ocupada, Reservada, Mantenimiento)
   - Capacidad de comensales

3. **Gestión de Menú**
   - Platos por categorías (Entradas, Fondos, Postres, Bebidas)
   - Control de precios y disponibilidad

4. **Gestión de Pedidos**
   - Creación de pedidos por mesa
   - Estados: Pendiente, En Preparación, Servido, Cerrado
   - Detalles de pedido

5. **Facturación**
   - Generación automática de facturas
   - Métodos de pago: Efectivo, Tarjeta, Yape
   - Reportes de ventas

6. **Inventario de Insumos**
   - Control de stock
   - Alertas de stock mínimo
   - Registro de compras

7. **Seguridad y Auditoría**
   - Autenticación JWT
   - 4 roles: ADMIN, MOZO, COCINERO, CAJERO
   - Bitácora de operaciones con AOP

## Configuración Inicial

### 1. Requisitos Previos

- Java 17 o superior
- PostgreSQL 12 o superior
- Maven 3.6 o superior

### 2. Configurar Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE bd_restaurante;

-- Crear usuario (opcional)
CREATE USER restaurante_user WITH PASSWORD 'restaurante_pass';
GRANT ALL PRIVILEGES ON DATABASE bd_restaurante TO restaurante_user;
```

### 3. Configurar application.properties

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bd_restaurante
spring.datasource.username=postgres
spring.datasource.password=tu_password
```

### 4. Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8090/api`

## Usuarios Predefinidos

| Usuario    | Contraseña   | Rol        |
|------------|--------------|------------|
| admin      | password123  | ADMIN      |
| mozo1      | password123  | MOZO       |
| cocinero1  | password123  | COCINERO   |
| cajero1    | password123  | CAJERO     |

## Documentación API

Acceder a Swagger UI: `http://localhost:8090/api/swagger-ui.html`

## Endpoints Principales

### Autenticación

```http
POST /api/auth/login
Content-Type: application/json

{
  "nombreUsuario": "admin",
  "contrasena": "password123"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "tipo": "Bearer",
  "nombreUsuario": "admin",
  "roles": ["ROLE_ADMIN"]
}
```

### Ejemplos de Uso

#### 1. Crear un Pedido

```http
POST /api/pedidos
Authorization: Bearer {token}
Content-Type: application/json

{
  "mesa": {
    "idMesa": 1
  },
  "cliente": {
    "idCliente": 1
  }
}
```

#### 2. Agregar Platos al Pedido

```http
POST /api/pedidos/1/detalles?idPlato=4&cantidad=2
Authorization: Bearer {token}
```

#### 3. Cambiar Estado del Pedido

```http
PATCH /api/pedidos/1/estado?estado=EN_PREPARACION
Authorization: Bearer {token}
```

#### 4. Generar Factura

```http
POST /api/facturas?idPedido=1&metodoPago=EFECTIVO
Authorization: Bearer {token}
```

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/eva3/evalu_03/
│   │   ├── aspect/          # AOP - Bitácora
│   │   ├── config/          # Configuraciones
│   │   ├── controller/      # Controladores REST
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # Entidades JPA
│   │   ├── exception/       # Manejo de excepciones
│   │   ├── repository/      # Repositorios JPA
│   │   ├── security/        # Configuración de seguridad
│   │   └── service/         # Lógica de negocio
│   └── resources/
│       ├── application.properties
│       └── data.sql         # Datos iniciales
```

## Roles y Permisos

| Endpoint          | ADMIN | MOZO | COCINERO | CAJERO |
|-------------------|-------|------|----------|--------|
| /api/clientes/**  | ✓     | ✓    | ✗        | ✗      |
| /api/mesas/**     | ✓     | ✓    | ✗        | ✗      |
| /api/platos/**    | ✓     | ✓    | ✓        | ✗      |
| /api/pedidos/**   | ✓     | ✓    | ✓        | ✗      |
| /api/facturas/**  | ✓     | ✗    | ✗        | ✓      |
| /api/insumos/**   | ✓     | ✗    | ✓        | ✗      |
| /api/proveedores/**| ✓    | ✗    | ✗        | ✗      |
| /api/compras/**   | ✓     | ✗    | ✗        | ✗      |
| /api/bitacora/**  | ✓     | ✗    | ✗        | ✗      |

## Características de Seguridad

- **Autenticación JWT**: Tokens con expiración de 24 horas
- **Contraseñas encriptadas**: BCrypt
- **Control de acceso basado en roles**: @PreAuthorize
- **CORS configurado**: Para frontends en localhost:3000 y localhost:4200
- **Auditoría AOP**: Registro automático de operaciones

## Auditoría (Bitácora)

El sistema registra automáticamente todas las operaciones importantes mediante AOP:

- Usuario que realizó la acción
- Método ejecutado
- Fecha y hora
- IP del cliente
- Resultado (exitoso/fallido)

Consultar bitácora:
```http
GET /api/bitacora
GET /api/bitacora/usuario/{usuario}
GET /api/bitacora/periodo?inicio=2024-01-01T00:00:00&fin=2024-12-31T23:59:59
```

## Base de Datos

El sistema crea automáticamente las siguientes tablas:

- clientes
- mesas
- platos
- insumos
- plato_insumo
- pedidos
- detalle_pedido
- facturas
- detalle_factura
- proveedores
- compras
- detalle_compra
- usuarios
- roles
- usuario_rol
- bitacora

## Datos de Prueba

El archivo `data.sql` incluye:
- 4 roles
- 4 usuarios de prueba
- 10 mesas
- 15 platos (entradas, fondos, postres, bebidas)
- 12 insumos
- 5 clientes
- 3 proveedores

## Flujo de Trabajo Típico

1. **Mozo** inicia sesión
2. **Mozo** crea un pedido para una mesa
3. **Mozo** agrega platos al pedido
4. **Cocinero** ve los pedidos pendientes
5. **Cocinero** cambia estado a "En Preparación"
6. **Mozo** cambia estado a "Servido"
7. **Cajero** genera la factura
8. Mesa queda disponible automáticamente

## Troubleshooting

### Error de conexión a la base de datos
- Verificar que PostgreSQL esté ejecutándose
- Verificar credenciales en application.properties
- Verificar que la base de datos bd_restaurante exista

### Error 401 Unauthorized
- Verificar que el token JWT sea válido
- El token expira después de 24 horas

### Error 403 Forbidden
- Verificar que el usuario tenga los permisos necesarios
- Revisar la tabla de roles y permisos

## Contacto y Soporte

- Proyecto: Sistema de Gestión de Restaurante "Sabor Gourmet"
- Versión: 1.0.0
- Puerto: 8090

## Licencia

Este proyecto es un sistema educativo desarrollado para fines académicos.
