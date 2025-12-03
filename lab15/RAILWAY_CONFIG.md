# Configuración de Variables de Entorno en Railway

## Problema Actual
Los servicios están "durmiendo" (sleeping/crashed) porque les faltan las variables de entorno para conectarse a PostgreSQL y entre sí.

## Paso 1: Crear Base de Datos PostgreSQL en Railway

1. En tu proyecto de Railway, haz clic en **"+ New"**
2. Selecciona **"Database"**
3. Selecciona **"Add PostgreSQL"**
4. Railway creará automáticamente una base de datos y generará las credenciales

## Paso 2: Obtener URLs Públicas de los Servicios

Para cada servicio que ya esté desplegado:

1. Haz clic en el servicio (eureka-server, api-gateway, etc.)
2. Ve a la pestaña **"Settings"**
3. Busca la sección **"Networking"**
4. Haz clic en **"Generate Domain"** para crear una URL pública
5. Anota la URL generada (ejemplo: `eureka-server-production.up.railway.app`)

## Paso 3: Configurar Variables de Entorno

### Para EUREKA-SERVER:
No necesita variables de entorno adicionales. Solo asegúrate de generar su dominio público.

### Para CATEGORIA-SERVICE:

Variables a agregar en Railway:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://<POSTGRES_HOST>:<POSTGRES_PORT>/<POSTGRES_DATABASE>
SPRING_DATASOURCE_USERNAME=<POSTGRES_USER>
SPRING_DATASOURCE_PASSWORD=<POSTGRES_PASSWORD>
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=https://<eureka-server-url>/eureka/
SERVER_PORT=8081
```

Ejemplo con valores reales:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres.railway.internal:5432/railway
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=tu_password_generado
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=https://eureka-server-production.up.railway.app/eureka/
SERVER_PORT=8081
```

### Para PRODUCTO-SERVICE:

Variables a agregar en Railway:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://<POSTGRES_HOST>:<POSTGRES_PORT>/<POSTGRES_DATABASE>
SPRING_DATASOURCE_USERNAME=<POSTGRES_USER>
SPRING_DATASOURCE_PASSWORD=<POSTGRES_PASSWORD>
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=https://<eureka-server-url>/eureka/
SERVER_PORT=8082
```

### Para API-GATEWAY:

Variables a agregar en Railway:
```
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=https://<eureka-server-url>/eureka/
SERVER_PORT=8080
```

### Para FRONTEND:

Variables a agregar en Railway:
```
VITE_API_URL=https://<api-gateway-url>
```

## Paso 4: Cómo Agregar Variables de Entorno

1. En Railway, haz clic en el servicio (ejemplo: categoria-service)
2. Ve a la pestaña **"Variables"**
3. Haz clic en **"+ New Variable"**
4. Agrega cada variable con su nombre y valor
5. Haz clic en **"Add"** para cada una
6. Railway automáticamente redesplegará el servicio

## Paso 5: Obtener Credenciales de PostgreSQL

1. Haz clic en el servicio **"PostgreSQL"** en Railway
2. Ve a la pestaña **"Variables"**
3. Allí encontrarás las variables generadas:
   - `PGHOST` o `POSTGRES_HOST`
   - `PGPORT` o `POSTGRES_PORT`
   - `PGDATABASE` o `POSTGRES_DB`
   - `PGUSER` o `POSTGRES_USER`
   - `PGPASSWORD` o `POSTGRES_PASSWORD`
4. También verás `DATABASE_URL` que contiene toda la información

## Paso 6: Usar Railway Internal Network

Para conexiones entre servicios dentro de Railway, usa los nombres internos:
- Host de PostgreSQL: `postgres.railway.internal` (en lugar del host público)
- Esto es más rápido y seguro

## Paso 7: Verificar el Despliegue

Después de configurar las variables:
1. Los servicios se redesplegarán automáticamente
2. Verifica los logs en la pestaña **"Deployments"**
3. Si ves "Application started" o "Started [ServiceName]", el servicio está funcionando
4. Genera los dominios públicos para los servicios que necesites exponer:
   - eureka-server (para ver el dashboard)
   - api-gateway (para las peticiones del frontend)
   - frontend (para acceder a la aplicación)

## Paso 8: Poblar las Bases de Datos

Una vez que categoria-service y producto-service estén funcionando:
1. Usa los endpoints POST para crear categorías y productos
2. O conéctate directamente a PostgreSQL usando las credenciales de Railway

## Orden de Configuración Recomendado

1. Crear PostgreSQL
2. Generar dominio para eureka-server
3. Configurar variables de eureka-server (si es necesario)
4. Configurar variables de categoria-service y producto-service
5. Configurar variables de api-gateway
6. Generar dominio para api-gateway
7. Configurar variables de frontend con la URL del api-gateway
8. Generar dominio para frontend

## Notas Importantes

- Las variables de entorno sobrescriben las configuraciones del application.properties
- Railway detecta automáticamente cuando cambias variables y redespliega
- Los servicios pueden tardar 1-2 minutos en iniciar después de configurar variables
- Si un servicio sigue crasheando, revisa los logs en la pestaña "Deployments"
