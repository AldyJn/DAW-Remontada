// CRUD de Alumnos con AJAX - Lab 11

// Variables globales
let modalAlumno;
let modalEliminar;
let alumnoIdEliminar = null;

// Inicialización cuando el DOM está listo
document.addEventListener('DOMContentLoaded', function() {
    console.log('Lab 11 - CRUD de Alumnos con AJAX');

    // Inicializar modales de Bootstrap 5
    const modalAlumnoElement = document.getElementById('modalAlumno');
    const modalEliminarElement = document.getElementById('modalEliminar');

    if (modalAlumnoElement) {
        modalAlumno = new bootstrap.Modal(modalAlumnoElement);
    }

    if (modalEliminarElement) {
        modalEliminar = new bootstrap.Modal(modalEliminarElement);
    }

    // Auto-hide alerts después de 5 segundos
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            alert.style.transition = 'opacity 0.5s';
            alert.style.opacity = '0';
            setTimeout(function() {
                alert.remove();
            }, 500);
        }, 5000);
    });
});

// Obtener token CSRF para peticiones AJAX
function getCsrfToken() {
    const metaTag = document.querySelector('meta[name="_csrf"]');
    return metaTag ? metaTag.getAttribute('content') : '';
}

function getCsrfHeader() {
    const metaTag = document.querySelector('meta[name="_csrf_header"]');
    return metaTag ? metaTag.getAttribute('content') : 'X-CSRF-TOKEN';
}

// Función para mostrar mensajes de éxito/error
function mostrarMensaje(mensaje, tipo = 'success') {
    // Eliminar alertas anteriores
    const alertasAnteriores = document.querySelectorAll('.alert-dynamic');
    alertasAnteriores.forEach(alerta => alerta.remove());

    const alerta = document.createElement('div');
    alerta.className = `alert alert-${tipo} alert-dismissible fade show alert-dynamic`;
    alerta.setAttribute('role', 'alert');
    alerta.innerHTML = `
        ${mensaje}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;

    const header = document.querySelector('header');
    if (header) {
        header.appendChild(alerta);
    }

    // Auto-ocultar después de 5 segundos
    setTimeout(() => {
        alerta.style.transition = 'opacity 0.5s';
        alerta.style.opacity = '0';
        setTimeout(() => alerta.remove(), 500);
    }, 5000);
}

// Abrir modal para crear alumno
function abrirModalCrear() {
    document.getElementById('alumnoId').value = '';
    document.getElementById('alumnoNombre').value = '';
    document.getElementById('alumnoCreditos').value = '';

    // Limpiar errores
    document.getElementById('alumnoNombre').classList.remove('is-invalid');
    document.getElementById('alumnoCreditos').classList.remove('is-invalid');
    document.getElementById('errorNombre').textContent = '';
    document.getElementById('errorCreditos').textContent = '';

    document.getElementById('modalAlumnoLabel').innerHTML = '<i class="bi bi-person-plus"></i> Crear Alumno';
    modalAlumno.show();
}

// Abrir modal para editar alumno
async function abrirModalEditar(id) {
    try {
        const response = await fetch(`/api/alumnos/${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                [getCsrfHeader()]: getCsrfToken()
            }
        });

        if (response.ok) {
            const alumno = await response.json();

            document.getElementById('alumnoId').value = alumno.id;
            document.getElementById('alumnoNombre').value = alumno.nombre;
            document.getElementById('alumnoCreditos').value = alumno.creditos;

            // Limpiar errores
            document.getElementById('alumnoNombre').classList.remove('is-invalid');
            document.getElementById('alumnoCreditos').classList.remove('is-invalid');
            document.getElementById('errorNombre').textContent = '';
            document.getElementById('errorCreditos').textContent = '';

            document.getElementById('modalAlumnoLabel').innerHTML = '<i class="bi bi-pencil"></i> Editar Alumno';
            modalAlumno.show();
        } else {
            mostrarMensaje('Error al cargar los datos del alumno', 'danger');
        }
    } catch (error) {
        console.error('Error:', error);
        mostrarMensaje('Error de conexión al cargar el alumno', 'danger');
    }
}

// Guardar alumno (crear o actualizar)
async function guardarAlumno() {
    const id = document.getElementById('alumnoId').value;
    const nombre = document.getElementById('alumnoNombre').value.trim();
    const creditos = document.getElementById('alumnoCreditos').value;

    // Limpiar errores previos
    document.getElementById('alumnoNombre').classList.remove('is-invalid');
    document.getElementById('alumnoCreditos').classList.remove('is-invalid');
    document.getElementById('errorNombre').textContent = '';
    document.getElementById('errorCreditos').textContent = '';

    // Validación básica
    if (!nombre || nombre.length < 3) {
        document.getElementById('alumnoNombre').classList.add('is-invalid');
        document.getElementById('errorNombre').textContent = 'El nombre debe tener al menos 3 caracteres';
        return;
    }

    if (!creditos || creditos < 0 || creditos > 100) {
        document.getElementById('alumnoCreditos').classList.add('is-invalid');
        document.getElementById('errorCreditos').textContent = 'Los créditos deben estar entre 0 y 100';
        return;
    }

    const alumno = {
        nombre: nombre,
        creditos: parseInt(creditos)
    };

    try {
        const url = id ? `/api/alumnos/${id}` : '/api/alumnos';
        const method = id ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                [getCsrfHeader()]: getCsrfToken()
            },
            body: JSON.stringify(alumno)
        });

        const data = await response.json();

        if (response.ok) {
            modalAlumno.hide();
            mostrarMensaje(data.message, 'success');

            // Actualizar la tabla
            if (id) {
                actualizarFilaTabla(id, data.alumno);
            } else {
                agregarFilaTabla(data.alumno);
            }
        } else {
            // Mostrar errores de validación
            if (data.nombre) {
                document.getElementById('alumnoNombre').classList.add('is-invalid');
                document.getElementById('errorNombre').textContent = data.nombre;
            }
            if (data.creditos) {
                document.getElementById('alumnoCreditos').classList.add('is-invalid');
                document.getElementById('errorCreditos').textContent = data.creditos;
            }
        }
    } catch (error) {
        console.error('Error:', error);
        mostrarMensaje('Error de conexión al guardar el alumno', 'danger');
    }
}

// Actualizar fila en la tabla
function actualizarFilaTabla(id, alumno) {
    const fila = document.getElementById(`fila-${id}`);
    if (fila) {
        fila.querySelector('.nombre-cell').textContent = alumno.nombre;
        fila.querySelector('.creditos-cell').textContent = alumno.creditos;

        // Efecto visual
        fila.classList.add('table-success');
        setTimeout(() => {
            fila.classList.remove('table-success');
        }, 2000);
    }
}

// Agregar nueva fila a la tabla
function agregarFilaTabla(alumno) {
    const tbody = document.querySelector('#tablaAlumnos tbody');
    if (!tbody) {
        location.reload();
        return;
    }

    const nuevaFila = document.createElement('tr');
    nuevaFila.id = `fila-${alumno.id}`;
    nuevaFila.innerHTML = `
        <td data-id="${alumno.id}">${alumno.id}</td>
        <td class="nombre-cell">${alumno.nombre}</td>
        <td class="creditos-cell">${alumno.creditos}</td>
        <td>
            <button class="btn btn-primary btn-sm"
                   onclick="abrirModalEditar(${alumno.id})"
                   title="Editar alumno">
                <i class="bi bi-pencil"></i> Editar
            </button>
            <button class="btn btn-danger btn-sm"
                   onclick="abrirModalEliminar(${alumno.id}, '${alumno.nombre}')"
                   title="Eliminar alumno">
                <i class="bi bi-trash"></i> Eliminar
            </button>
        </td>
    `;

    tbody.appendChild(nuevaFila);

    // Efecto visual
    nuevaFila.classList.add('table-success');
    setTimeout(() => {
        nuevaFila.classList.remove('table-success');
    }, 2000);

    // Ocultar mensaje de "No hay alumnos" si existe
    const alertaVacia = document.querySelector('.alert-info');
    if (alertaVacia) {
        alertaVacia.remove();
    }
}

// Abrir modal para confirmar eliminación
function abrirModalEliminar(id, nombre) {
    alumnoIdEliminar = id;
    document.getElementById('eliminarNombre').textContent = nombre;
    document.getElementById('eliminarId').textContent = id;
    modalEliminar.show();
}

// Confirmar y ejecutar eliminación
async function confirmarEliminar() {
    if (!alumnoIdEliminar) return;

    try {
        const response = await fetch(`/api/alumnos/${alumnoIdEliminar}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                [getCsrfHeader()]: getCsrfToken()
            }
        });

        const data = await response.json();

        if (response.ok) {
            modalEliminar.hide();
            mostrarMensaje(data.message, 'success');

            // Eliminar fila de la tabla
            const fila = document.getElementById(`fila-${alumnoIdEliminar}`);
            if (fila) {
                fila.style.transition = 'opacity 0.5s';
                fila.style.opacity = '0';
                setTimeout(() => {
                    fila.remove();

                    // Si no quedan filas, mostrar mensaje
                    const tbody = document.querySelector('#tablaAlumnos tbody');
                    if (tbody && tbody.children.length === 0) {
                        const container = document.querySelector('.card-body');
                        if (container) {
                            const alerta = document.createElement('div');
                            alerta.className = 'alert alert-info';
                            alerta.innerHTML = '<i class="bi bi-info-circle"></i> No hay alumnos en la base de datos!';
                            container.insertBefore(alerta, container.firstChild.nextSibling);
                        }

                        const tabla = document.querySelector('.table-responsive');
                        if (tabla) tabla.remove();
                    }
                }, 500);
            }

            alumnoIdEliminar = null;
        } else {
            mostrarMensaje(data.error || 'Error al eliminar el alumno', 'danger');
        }
    } catch (error) {
        console.error('Error:', error);
        mostrarMensaje('Error de conexión al eliminar el alumno', 'danger');
    }
}
