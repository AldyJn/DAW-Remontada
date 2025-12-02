# Guía de Deployment - Microservicios

## Arquitectura del Sistema

Este proyecto consta de los siguientes servicios:

- **Eureka Server** (Puerto 8761) - Service Discovery
- **API Gateway** (Puerto 8080) - Punto de entrada único
- **Categoria Service** (Puerto 8081) - Gestión de categorías
- **Producto Service** (Puerto 8082) - Gestión de productos
- **Frontend** (React + Vite) - Interfaz de usuario
- **PostgreSQL** - Base de datos para categorías y productos

## Opción 1: Deployment en Railway

### Prerrequisitos

1. Cuenta en Railway (https://railway.app)
2. Cuenta en GitHub
3. Repositorio Git con el código

### Pasos para Deployment

#### 1. Preparar el Repositorio

```bash
git add .
git commit -m "Add deployment configuration"
git push origin main
```

#### 2. Crear Proyecto en Railway

1. Ir a https://railway.app
2. Click en "New Project"
3. Seleccionar "Deploy from GitHub repo"
4. Autorizar Railway para acceder a tu repositorio
5. Seleccionar el repositorio del proyecto

#### 3. Configurar PostgreSQL

1. En Railway, click en "+ New"
2. Seleccionar "Database" > "PostgreSQL"
3. Esperar a que se aprovisione
4. Railway creará automáticamente las variables de entorno

#### 4. Crear Servicios

Para cada microservicio, repetir:

1. Click en "+ New" > "Empty Service"
2. Nombrar el servicio (eureka-server, categoria-service, producto-service, api-gateway, frontend)
3. En Settings > Source, conectar el repositorio
4. En Settings > Build, configurar:
   - **Root Directory**: Especificar la carpeta del servicio
     - eureka-server: `microservicio_backend/eureka-server`
     - categoria-service: `microservicio_backend/categoria-service`
     - producto-service: `microservicio_backend/producto-service`
     - api-gateway: `microservicio_backend/api-gateway`
     - frontend: `frontend`

#### 5. Configurar Variables de Entorno

Para **categoria-service**:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://[POSTGRES_HOST]:[PORT]/railway
SPRING_DATASOURCE_USERNAME=[POSTGRES_USER]
SPRING_DATASOURCE_PASSWORD=[POSTGRES_PASSWORD]
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://[EUREKA_URL]:8761/eureka/
```

Para **producto-service**:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://[POSTGRES_HOST]:[PORT]/railway
SPRING_DATASOURCE_USERNAME=[POSTGRES_USER]
SPRING_DATASOURCE_PASSWORD=[POSTGRES_PASSWORD]
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://[EUREKA_URL]:8761/eureka/
```

Para **api-gateway**:
```
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://[EUREKA_URL]:8761/eureka/
```

#### 6. Orden de Deployment

1. Primero: PostgreSQL (esperar a que esté listo)
2. Segundo: Eureka Server (esperar a que esté listo)
3. Tercero: Categoria Service y Producto Service (en paralelo)
4. Cuarto: API Gateway
5. Quinto: Frontend

#### 7. Obtener URLs Públicas

1. En cada servicio, ir a Settings > Networking
2. Click en "Generate Domain"
3. Railway asignará un dominio público (ej: `https://servicio.up.railway.app`)

## Opción 2: Deployment con Docker Compose (Local/VPS)

### Prerrequisitos

- Docker instalado
- Docker Compose instalado

### Crear docker-compose.yml

Crear archivo `docker-compose.yml` en la raíz del proyecto:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:17-alpine
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 123
      POSTGRES_DB: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - microservices-network

  eureka-server:
    build:
      context: ./microservicio_backend/eureka-server
      dockerfile: Dockerfile
    ports:
      - "8761:8761"
    networks:
      - microservices-network
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8761/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 5

  categoria-service:
    build:
      context: ./microservicio_backend/categoria-service
      dockerfile: Dockerfile
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/postgres
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: 123
      EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE: http://eureka-server:8761/eureka/
    ports:
      - "8081:8081"
    depends_on:
      - postgres
      - eureka-server
    networks:
      - microservices-network

  producto-service:
    build:
      context: ./microservicio_backend/producto-service
      dockerfile: Dockerfile
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/postgres
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: 123
      EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE: http://eureka-server:8761/eureka/
    ports:
      - "8082:8082"
    depends_on:
      - postgres
      - eureka-server
    networks:
      - microservices-network

  api-gateway:
    build:
      context: ./microservicio_backend/api-gateway
      dockerfile: Dockerfile
    environment:
      EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE: http://eureka-server:8761/eureka/
    ports:
      - "8080:8080"
    depends_on:
      - eureka-server
      - categoria-service
      - producto-service
    networks:
      - microservices-network

  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    ports:
      - "80:80"
    depends_on:
      - api-gateway
    networks:
      - microservices-network

networks:
  microservices-network:
    driver: bridge

volumes:
  postgres_data:
```

### Comandos de Deployment

```bash
# Construir las imágenes
docker-compose build

# Iniciar todos los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down

# Detener y eliminar volúmenes
docker-compose down -v
```

## GitHub Actions - CI/CD Automático

### Configurar Secrets en GitHub

1. Ir a tu repositorio en GitHub
2. Settings > Secrets and variables > Actions
3. Agregar los siguientes secrets:
   - `RAILWAY_TOKEN`: Token de Railway (obtener en Account Settings > Tokens)
   - `RAILWAY_PROJECT_ID`: ID del proyecto en Railway

### Activar Workflow

El archivo `.github/workflows/deploy.yml` ya está configurado. Cada vez que hagas push a la rama `main`, se ejecutará automáticamente:

1. Build de todos los servicios
2. Ejecutar tests (si están configurados)
3. Deploy a Railway

## Verificación del Deployment

### URLs de los Servicios (Railway)

Una vez deployado, tendrás URLs similares a:

- Eureka Server: `https://eureka-server-production.up.railway.app`
- API Gateway: `https://api-gateway-production.up.railway.app`
- Frontend: `https://frontend-production.up.railway.app`

### Healthchecks

Verificar que los servicios estén funcionando:

```bash
# Eureka Server
curl https://[eureka-url]/actuator/health

# Categorías
curl https://[api-gateway-url]/api/categorias

# Productos
curl https://[api-gateway-url]/api/productos
```

## Inicializar Base de Datos

Después del primer deployment, inicializar las bases de datos:

```bash
# Crear bases de datos
# Conectarse a PostgreSQL en Railway y ejecutar:
CREATE DATABASE db_categoria;
CREATE DATABASE db_producto;

# O usar el script de inicialización de datos
curl -X POST https://[categoria-service-url]/api/categorias \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Laptops"}'
```

## Troubleshooting

### Servicio no se conecta a Eureka

- Verificar que `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` esté configurado correctamente
- Verificar que Eureka Server esté corriendo primero

### Error de conexión a base de datos

- Verificar variables de entorno de PostgreSQL
- Verificar que PostgreSQL esté corriendo
- Revisar logs del servicio: `railway logs [service-name]`

### Frontend no se conecta al backend

- Verificar configuración de CORS en API Gateway
- Verificar que las URLs del backend estén correctas en el frontend
- Revisar configuración de nginx.conf

## Costos Estimados

### Railway (Plan Gratuito)

- 500 horas de ejecución por mes
- $5 de crédito gratis
- Suficiente para desarrollo y demos

### Railway (Plan Hobby - $5/mes)

- $5 de crédito incluido
- Pay-as-you-go después
- Ideal para producción pequeña

## Monitoreo

### Railway Dashboard

- Ver logs en tiempo real
- Métricas de CPU y memoria
- Tráfico de red

### Logs

```bash
# Ver logs de un servicio específico
railway logs [service-name] --tail 100

# Seguir logs en tiempo real
railway logs [service-name] --follow
```

## Rollback

Si algo sale mal:

```bash
# En Railway, ir a Deployments
# Seleccionar un deployment anterior
# Click en "Redeploy"
```

## Actualización de Servicios

```bash
# Hacer cambios en el código
git add .
git commit -m "Update service"
git push origin main

# GitHub Actions desplegará automáticamente
```
