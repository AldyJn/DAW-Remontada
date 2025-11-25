# MicroStore Frontend

Frontend moderno y profesional para el sistema de gestión de productos y categorías con microservicios.

## 🚀 Características

- ✨ Interfaz moderna y responsiva con Tailwind CSS
- 🎨 Animaciones suaves con Framer Motion
- 📱 Diseño mobile-first
- 🔄 Integración con API REST
- 🎯 Gestión de categorías y productos
- 🔍 Búsqueda en tiempo real
- 📊 Tabla interactiva de productos
- 🎪 Modales y notificaciones elegantes
- ⚡ Rendimiento optimizado con Vite

## 📋 Requisitos Previos

- Node.js 16+ 
- npm o yarn
- Backend ejecutándose en `http://localhost:8080`

## 🛠️ Instalación

1. Instala las dependencias:

```bash
npm install
```

2. Instala las dependencias adicionales necesarias:

```bash
npm install axios react-router-dom lucide-react framer-motion tailwindcss postcss autoprefixer
```

## 🚀 Desarrollo

Inicia el servidor de desarrollo:

```bash
npm run dev
```

La aplicación estará disponible en `http://localhost:5173`

## 📦 Build para Producción

```bash
npm run build
```

Los archivos compilados estar��n en la carpeta `dist/`

## 🏗️ Estructura del Proyecto

```
frontend/
├── src/
│   ├── components/          # Componentes reutilizables
│   │   ├── Navbar.jsx
│   │   ├── Modal.jsx
│   │   ├── Toast.jsx
│   │   └── LoadingSpinner.jsx
│   ├── pages/              # Páginas principales
│   │   ├── Home.jsx
│   │   ├── Categorias.jsx
│   │   └── Productos.jsx
│   ├── services/           # Servicios API
│   │   └── api.js
│   ├── App.jsx             # Componente principal
│   ├── main.jsx            # Punto de entrada
│   └── index.css           # Estilos globales
├── index.html
├── package.json
├── tailwind.config.js
├── postcss.config.js
└── vite.config.js
```

## 🎨 Tecnologías Utilizadas

- **React 19** - Librería UI
- **Vite** - Build tool
- **Tailwind CSS** - Framework CSS
- **Framer Motion** - Animaciones
- **Axios** - Cliente HTTP
- **React Router** - Enrutamiento
- **Lucide React** - Iconos

## 🔌 Configuración de API

El frontend se conecta al API Gateway en `http://localhost:8080/api`

Endpoints disponibles:
- `GET /api/categorias` - Obtener todas las categorías
- `POST /api/categorias` - Crear categoría
- `PUT /api/categorias/:id` - Actualizar categoría
- `DELETE /api/categorias/:id` - Eliminar categoría
- `GET /api/productos` - Obtener todos los productos
- `POST /api/productos` - Crear producto
- `PUT /api/productos/:id` - Actualizar producto
- `DELETE /api/productos/:id` - Eliminar producto

## 📱 Páginas

### Home
Página de inicio con información general del sistema y características principales.

### Categorías
Gestión completa de categorías:
- Listar todas las categorías
- Crear nuevas categorías
- Editar categorías existentes
- Eliminar categorías
- Búsqueda en tiempo real

### Productos
Gestión completa de productos:
- Listar todos los productos en tabla interactiva
- Crear nuevos productos
- Editar productos existentes
- Eliminar productos
- Búsqueda en tiempo real
- Visualización de precio, stock y categoría

## 🎯 Características Destacadas

### Diseño Responsivo
- Adaptado para desktop, tablet y móvil
- Navegación intuitiva
- Interfaz limpia y moderna

### Animaciones
- Transiciones suaves
- Efectos hover elegantes
- Animaciones de carga
- Modales animados

### Validación
- Validación de formularios
- Mensajes de error claros
- Confirmación de acciones destructivas

### Notificaciones
- Toast notifications para feedback
- Diferentes tipos: success, error, warning, info
- Auto-cierre después de 3 segundos

## 🚀 Despliegue

Para desplegar en producción:

1. Build la aplicación:
```bash
npm run build
```

2. Sirve los archivos estáticos desde la carpeta `dist/`

3. Configura las variables de entorno según sea necesario

## 📝 Notas

- Asegúrate de que el backend esté ejecutándose antes de iniciar el frontend
- El API Gateway debe estar en `http://localhost:8080`
- CORS está configurado para aceptar solicitudes desde `localhost:5173`

## 👨‍💻 Autor

Desarrollado como parte del proyecto de microservicios.

## 📄 Licencia

MIT
