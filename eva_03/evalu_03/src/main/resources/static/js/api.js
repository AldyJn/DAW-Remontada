// Función para hacer peticiones a la API
async function apiRequest(endpoint, method = 'GET', body = null) {
    const headers = {
        'Content-Type': 'application/json'
    };

    const token = getToken();
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = {
        method,
        headers
    };

    if (body && (method === 'POST' || method === 'PUT' || method === 'PATCH')) {
        config.body = JSON.stringify(body);
    }

    try {
        const response = await fetch(`${API_URL}${endpoint}`, config);

        // Si es 401, redirigir a login
        if (response.status === 401) {
            clearSession();
            window.location.href = '/index.html';
            throw new Error('Sesión expirada');
        }

        // Si es 403, error de permisos (NO cerrar sesión)
        if (response.status === 403) {
            throw new Error('No tienes permisos para acceder a este recurso');
        }

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.mensaje || 'Error en la petición');
        }

        return data;
    } catch (error) {
        console.error('Error en API:', error);
        throw error;
    }
}

// Funciones específicas de la API
const API = {
    // Autenticación
    login: (username, password) => apiRequest('/auth/login', 'POST', {
        nombreUsuario: username,
        contrasena: password
    }),

    // Mesas
    getMesas: () => apiRequest('/mesas'),
    getMesa: (id) => apiRequest(`/mesas/${id}`),
    createMesa: (mesa) => apiRequest('/mesas', 'POST', mesa),
    updateMesa: (id, mesa) => apiRequest(`/mesas/${id}`, 'PUT', mesa),
    deleteMesa: (id) => apiRequest(`/mesas/${id}`, 'DELETE'),

    // Pedidos
    getPedidos: () => apiRequest('/pedidos'),
    getPedido: (id) => apiRequest(`/pedidos/${id}`),
    createPedido: (pedido) => apiRequest('/pedidos', 'POST', pedido),
    updatePedido: (id, pedido) => apiRequest(`/pedidos/${id}`, 'PUT', pedido),
    deletePedido: (id) => apiRequest(`/pedidos/${id}`, 'DELETE'),

    // Platos
    getPlatos: () => apiRequest('/platos'),
    getPlato: (id) => apiRequest(`/platos/${id}`),
    createPlato: (plato) => apiRequest('/platos', 'POST', plato),
    updatePlato: (id, plato) => apiRequest(`/platos/${id}`, 'PUT', plato),
    deletePlato: (id) => apiRequest(`/platos/${id}`, 'DELETE'),

    // Clientes
    getClientes: () => apiRequest('/clientes'),
    getCliente: (id) => apiRequest(`/clientes/${id}`),
    createCliente: (cliente) => apiRequest('/clientes', 'POST', cliente),
    updateCliente: (id, cliente) => apiRequest(`/clientes/${id}`, 'PUT', cliente),
    deleteCliente: (id) => apiRequest(`/clientes/${id}`, 'DELETE'),

    // Insumos
    getInsumos: () => apiRequest('/insumos'),
    getInsumo: (id) => apiRequest(`/insumos/${id}`),
    createInsumo: (insumo) => apiRequest('/insumos', 'POST', insumo),
    updateInsumo: (id, insumo) => apiRequest(`/insumos/${id}`, 'PUT', insumo),
    deleteInsumo: (id) => apiRequest(`/insumos/${id}`, 'DELETE'),

    // Facturas
    getFacturas: () => apiRequest('/facturas'),
    getFactura: (id) => apiRequest(`/facturas/${id}`),
    createFactura: (idPedido, metodoPago) => apiRequest(`/facturas?idPedido=${idPedido}&metodoPago=${metodoPago}`, 'POST')
};
