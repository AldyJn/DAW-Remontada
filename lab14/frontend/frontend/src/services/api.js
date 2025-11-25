import axios from 'axios';

// Servicios para Categorías
const categoriaApi = axios.create({
  baseURL: 'http://localhost:8081/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Servicios para Productos
const productoApi = axios.create({
  baseURL: 'http://localhost:8082/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Servicios para Categorías
export const categoriaService = {
  getAll: () => categoriaApi.get('/categorias'),
  getById: (id) => categoriaApi.get(`/categorias/${id}`),
  create: (data) => categoriaApi.post('/categorias', data),
  update: (id, data) => categoriaApi.put(`/categorias/${id}`, data),
  delete: (id) => categoriaApi.delete(`/categorias/${id}`),
};

// Servicios para Productos
export const productoService = {
  getAll: () => productoApi.get('/productos'),
  getById: (id) => productoApi.get(`/productos/${id}`),
  create: (data) => productoApi.post('/productos', data),
  update: (id, data) => productoApi.put(`/productos/${id}`, data),
  delete: (id) => productoApi.delete(`/productos/${id}`),
};

export default categoriaApi;