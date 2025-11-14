// Configuración de la API
const API_URL = 'http://localhost:8090/api';

// Funciones de localStorage
function getToken() {
    return localStorage.getItem('token');
}

function saveToken(token) {
    localStorage.setItem('token', token);
}

function saveUser(username, roles) {
    localStorage.setItem('username', username);
    localStorage.setItem('roles', JSON.stringify(roles));
}

function isAuthenticated() {
    return !!getToken();
}

// Función de login
async function handleLogin() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');
    const loginBtn = document.getElementById('loginBtn');

    // Ocultar mensaje de error
    errorMessage.style.display = 'none';

    // Deshabilitar botón
    loginBtn.disabled = true;
    loginBtn.textContent = 'Iniciando...';

    console.log('Iniciando login con usuario:', username);

    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                nombreUsuario: username,
                contrasena: password
            })
        });

        console.log('Status de respuesta:', response.status);

        const data = await response.json();
        console.log('Datos recibidos:', data);

        if (!response.ok) {
            throw new Error(data.mensaje || 'Usuario o contraseña incorrectos');
        }

        // Guardar token y datos de usuario
        saveToken(data.token);
        saveUser(data.nombreUsuario, data.roles);

        console.log('Login exitoso, redirigiendo...');

        // Redirigir al dashboard
        window.location.href = '/dashboard.html';

    } catch (error) {
        console.error('Error en login:', error);
        errorMessage.textContent = error.message;
        errorMessage.style.display = 'block';

        // Rehabilitar botón
        loginBtn.disabled = false;
        loginBtn.textContent = 'Iniciar Sesión';
    }
}

// Inicializar cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM cargado');

    // Si ya está autenticado, redirigir al dashboard
    if (isAuthenticated()) {
        console.log('Ya autenticado, redirigiendo...');
        window.location.href = '/dashboard.html';
        return;
    }

    // Agregar evento al botón de login
    const loginBtn = document.getElementById('loginBtn');
    if (loginBtn) {
        console.log('Botón de login encontrado, agregando evento');
        loginBtn.addEventListener('click', handleLogin);
    } else {
        console.error('No se encontró el botón de login');
    }

    // También permitir login con Enter
    const passwordInput = document.getElementById('password');
    if (passwordInput) {
        passwordInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                handleLogin();
            }
        });
    }
});
