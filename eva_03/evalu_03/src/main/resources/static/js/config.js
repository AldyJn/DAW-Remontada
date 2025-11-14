// Configuración de la API
const API_URL = 'http://localhost:8090/api';

// Obtener token del localStorage
function getToken() {
    return localStorage.getItem('token');
}

// Guardar token en localStorage
function saveToken(token) {
    localStorage.setItem('token', token);
}

// Guardar usuario en localStorage
function saveUser(username, roles) {
    localStorage.setItem('username', username);
    localStorage.setItem('roles', JSON.stringify(roles));
}

// Obtener usuario del localStorage
function getUser() {
    return {
        username: localStorage.getItem('username'),
        roles: JSON.parse(localStorage.getItem('roles') || '[]')
    };
}

// Limpiar datos de sesión
function clearSession() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('roles');
}

// Verificar si está autenticado
function isAuthenticated() {
    return !!getToken();
}

// Redirigir a login si no está autenticado
function requireAuth() {
    if (!isAuthenticated()) {
        window.location.href = '/index.html';
        return false;
    }
    return true;
}
