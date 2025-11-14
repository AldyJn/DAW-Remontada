// Configurar menú según roles
function setupMenuByRole() {
    const user = getUser();
    const roles = user.roles;

    // Definir permisos por rol
    const permissions = {
        'ROLE_ADMIN': ['dashboard', 'mesas', 'pedidos', 'platos', 'clientes', 'insumos'],
        'ROLE_MOZO': ['dashboard', 'mesas', 'pedidos', 'platos', 'clientes'],
        'ROLE_COCINERO': ['dashboard', 'pedidos', 'platos', 'insumos'],
        'ROLE_CAJERO': ['dashboard', 'pedidos', 'facturas']
    };

    // Obtener permisos del usuario
    let userPermissions = [];
    roles.forEach(role => {
        if (permissions[role]) {
            userPermissions = [...new Set([...userPermissions, ...permissions[role]])];
        }
    });

    // Ocultar opciones sin permiso
    document.querySelectorAll('.nav-item').forEach(item => {
        const page = item.dataset.page;
        if (page && !userPermissions.includes(page)) {
            item.style.display = 'none';
        }
    });
}

// Dashboard main logic
document.addEventListener('DOMContentLoaded', async () => {
    // Verificar autenticación
    if (!requireAuth()) return;

    // Obtener datos del usuario
    const user = getUser();
    document.getElementById('userName').textContent = user.username;
    document.getElementById('userRole').textContent = user.roles.join(', ').replace(/ROLE_/g, '');

    // Configurar menú según roles
    setupMenuByRole();

    // Cargar estadísticas del dashboard
    loadDashboardStats();

    // Configurar navegación
    setupNavigation();

    // Configurar logout
    document.getElementById('logoutBtn').addEventListener('click', () => {
        clearSession();
        window.location.href = '/index.html';
    });
});

// Cargar estadísticas del dashboard
async function loadDashboardStats() {
    try {
        const [mesas, pedidos, platos, clientes] = await Promise.all([
            API.getMesas(),
            API.getPedidos(),
            API.getPlatos(),
            API.getClientes()
        ]);

        document.getElementById('totalMesas').textContent =
            mesas.filter(m => m.estado === 'DISPONIBLE').length;
        document.getElementById('pedidosActivos').textContent =
            pedidos.filter(p => p.estado !== 'CERRADO').length;
        document.getElementById('totalPlatos').textContent = platos.length;
        document.getElementById('totalClientes').textContent = clientes.length;
    } catch (error) {
        console.error('Error cargando estadísticas:', error);
    }
}

// Configurar navegación del sidebar
function setupNavigation() {
    const navItems = document.querySelectorAll('.nav-item');
    const contentArea = document.getElementById('contentArea');
    const pageTitle = document.getElementById('pageTitle');

    navItems.forEach(item => {
        item.addEventListener('click', async (e) => {
            e.preventDefault();

            // Actualizar clases activas
            navItems.forEach(nav => nav.classList.remove('active'));
            item.classList.add('active');

            // Obtener página a cargar
            const page = item.dataset.page;
            pageTitle.textContent = item.textContent.trim();

            // Cargar contenido según la página
            switch (page) {
                case 'dashboard':
                    loadDashboard();
                    break;
                case 'mesas':
                    await loadMesas();
                    break;
                case 'pedidos':
                    await loadPedidos();
                    break;
                case 'platos':
                    await loadPlatos();
                    break;
                case 'clientes':
                    await loadClientes();
                    break;
                case 'insumos':
                    await loadInsumos();
                    break;
                case 'facturas':
                    await loadFacturas();
                    break;
            }
        });
    });
}

// Cargar vista de dashboard
function loadDashboard() {
    const contentArea = document.getElementById('contentArea');
    contentArea.innerHTML = `
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon">M</div>
                <div class="stat-info">
                    <h3 id="totalMesas">0</h3>
                    <p>Mesas Disponibles</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">P</div>
                <div class="stat-info">
                    <h3 id="pedidosActivos">0</h3>
                    <p>Pedidos Activos</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">F</div>
                <div class="stat-info">
                    <h3 id="totalPlatos">0</h3>
                    <p>Platos en Menú</p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon">C</div>
                <div class="stat-info">
                    <h3 id="totalClientes">0</h3>
                    <p>Clientes Registrados</p>
                </div>
            </div>
        </div>
        <div class="welcome-message">
            <h2>Bienvenido al Sistema de Gestión</h2>
            <p>Selecciona una opción del menú lateral para comenzar</p>
        </div>
    `;
    loadDashboardStats();
}

// Cargar vista de mesas
async function loadMesas() {
    const contentArea = document.getElementById('contentArea');
    contentArea.innerHTML = '<div class="loading">Cargando mesas...</div>';

    try {
        const mesas = await API.getMesas();
        const user = getUser();
        const canManage = user.roles.includes('ROLE_ADMIN') || user.roles.includes('ROLE_MOZO');

        const mesasHTML = mesas.map(mesa => {
            const estadoBadge = {
                'DISPONIBLE': 'badge-success',
                'OCUPADA': 'badge-danger',
                'RESERVADA': 'badge-warning',
                'MANTENIMIENTO': 'badge-info'
            }[mesa.estado];

            let acciones = '';
            if (canManage) {
                if (mesa.estado === 'DISPONIBLE') {
                    acciones = `
                        <button class="btn btn-sm btn-warning" onclick="cambiarEstadoMesa(${mesa.idMesa}, 'RESERVADA')">Reservar</button>
                    `;
                } else if (mesa.estado === 'OCUPADA') {
                    acciones = `
                        <button class="btn btn-sm btn-success" onclick="cambiarEstadoMesa(${mesa.idMesa}, 'DISPONIBLE')">Liberar</button>
                    `;
                } else if (mesa.estado === 'RESERVADA') {
                    acciones = `
                        <button class="btn btn-sm btn-primary" onclick="cambiarEstadoMesa(${mesa.idMesa}, 'OCUPADA')">Ocupar</button>
                        <button class="btn btn-sm btn-success" onclick="cambiarEstadoMesa(${mesa.idMesa}, 'DISPONIBLE')">Liberar</button>
                    `;
                }

                if (user.roles.includes('ROLE_ADMIN')) {
                    if (mesa.estado !== 'MANTENIMIENTO') {
                        acciones += `<button class="btn btn-sm btn-secondary" onclick="cambiarEstadoMesa(${mesa.idMesa}, 'MANTENIMIENTO')">Mantenimiento</button>`;
                    } else {
                        acciones = `<button class="btn btn-sm btn-success" onclick="cambiarEstadoMesa(${mesa.idMesa}, 'DISPONIBLE')">Activar</button>`;
                    }
                }
            }

            return `
                <div class="stat-card">
                    <div class="stat-icon">${mesa.numero}</div>
                    <div class="stat-info" style="width: 100%;">
                        <h3>Mesa ${mesa.numero}</h3>
                        <p>Capacidad: ${mesa.capacidad} personas</p>
                        <span class="badge ${estadoBadge}">${mesa.estado}</span>
                        ${canManage ? `<div style="margin-top: 10px;">${acciones}</div>` : ''}
                    </div>
                </div>
            `;
        }).join('');

        contentArea.innerHTML = `
            <div class="stats-grid">
                ${mesasHTML}
            </div>
        `;
    } catch (error) {
        contentArea.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">X</div>
                <p>Error al cargar las mesas: ${error.message}</p>
            </div>
        `;
    }
}

// Cargar vista de pedidos
async function loadPedidos() {
    const contentArea = document.getElementById('contentArea');
    contentArea.innerHTML = '<div class="loading">Cargando pedidos...</div>';

    try {
        const pedidos = await API.getPedidos();
        const user = getUser();
        const canCreate = user.roles.includes('ROLE_ADMIN') || user.roles.includes('ROLE_MOZO');
        const canChangeStatus = user.roles.includes('ROLE_ADMIN') || user.roles.includes('ROLE_MOZO') || user.roles.includes('ROLE_COCINERO');
        const canClose = user.roles.includes('ROLE_ADMIN') || user.roles.includes('ROLE_CAJERO');

        const pedidosRows = pedidos.map(pedido => {
            const estadoBadge = {
                'PENDIENTE': 'badge-warning',
                'EN_PREPARACION': 'badge-info',
                'SERVIDO': 'badge-success',
                'CERRADO': 'badge-secondary'
            }[pedido.estado] || 'badge-info';

            const fecha = new Date(pedido.fechaHora).toLocaleString('es-ES');

            let acciones = '';
            if (canChangeStatus && pedido.estado !== 'CERRADO') {
                if (pedido.estado === 'PENDIENTE') {
                    acciones = `<button class="btn btn-sm btn-primary" onclick="cambiarEstadoPedido(${pedido.idPedido}, 'EN_PREPARACION')">Preparar</button>`;
                } else if (pedido.estado === 'EN_PREPARACION') {
                    acciones = `<button class="btn btn-sm btn-success" onclick="cambiarEstadoPedido(${pedido.idPedido}, 'SERVIDO')">Servir</button>`;
                } else if (pedido.estado === 'SERVIDO' && canClose) {
                    acciones = `<button class="btn btn-sm btn-danger" onclick="cerrarPedidoConFactura(${pedido.idPedido})">Generar Factura</button>`;
                }
            }

            return `
                <tr>
                    <td>#${pedido.idPedido}</td>
                    <td>Mesa ${pedido.mesa?.numero || 'N/A'}</td>
                    <td>${pedido.cliente?.nombres || 'Sin cliente'}</td>
                    <td>${fecha}</td>
                    <td><span class="badge ${estadoBadge}">${pedido.estado}</span></td>
                    ${(canChangeStatus || canClose) ? `<td>${acciones}</td>` : ''}
                </tr>
            `;
        }).join('');

        contentArea.innerHTML = `
            <div class="table-container">
                <div class="table-header">
                    <h3>Pedidos Registrados</h3>
                    ${canCreate ? '<button class="btn btn-success" onclick="showPedidoModal()">Nuevo Pedido</button>' : ''}
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Mesa</th>
                            <th>Cliente</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                            ${(canChangeStatus || canClose) ? '<th>Acciones</th>' : ''}
                        </tr>
                    </thead>
                    <tbody>
                        ${pedidosRows.length > 0 ? pedidosRows : '<tr><td colspan="6" style="text-align: center;">No hay pedidos registrados</td></tr>'}
                    </tbody>
                </table>
            </div>
        `;
    } catch (error) {
        contentArea.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">X</div>
                <p>Error al cargar los pedidos: ${error.message}</p>
            </div>
        `;
    }
}

// Cargar vista de platos
async function loadPlatos() {
    const contentArea = document.getElementById('contentArea');
    contentArea.innerHTML = '<div class="loading">Cargando menú...</div>';

    try {
        const platos = await API.getPlatos();
        const user = getUser();
        const canEdit = user.roles.includes('ROLE_ADMIN');

        const platosPorTipo = {
            'ENTRADA': [],
            'FONDO': [],
            'POSTRE': [],
            'BEBIDA': []
        };

        platos.forEach(plato => {
            platosPorTipo[plato.tipo].push(plato);
        });

        let headerHTML = `
            <div class="table-header" style="margin-bottom: 20px;">
                <h3>Menú de Platos</h3>
                ${canEdit ? '<button class="btn btn-success" onclick="showPlatoModal()">Nuevo Plato</button>' : ''}
            </div>
        `;

        const platosHTML = Object.entries(platosPorTipo).map(([tipo, items]) => {
            const itemsHTML = items.map(plato => `
                <div class="stat-card">
                    <div class="stat-info" style="width: 100%;">
                        <h3>${plato.nombre}</h3>
                        <p>${plato.descripcion}</p>
                        <p><strong>S/ ${plato.precio.toFixed(2)}</strong></p>
                        <span class="badge ${plato.estado ? 'badge-success' : 'badge-danger'}">
                            ${plato.estado ? 'Disponible' : 'No disponible'}
                        </span>
                        ${canEdit ? `
                        <div style="margin-top: 10px;">
                            <button class="btn btn-sm btn-primary" onclick="editPlato(${plato.idPlato})">Editar</button>
                            <button class="btn btn-sm btn-danger" onclick="deletePlato(${plato.idPlato})">Eliminar</button>
                        </div>
                        ` : ''}
                    </div>
                </div>
            `).join('');

            return `
                <div style="margin-bottom: 30px;">
                    <h2 style="color: var(--primary); margin-bottom: 15px;">${tipo}S</h2>
                    <div class="stats-grid">
                        ${itemsHTML || '<p>No hay platos en esta categoría</p>'}
                    </div>
                </div>
            `;
        }).join('');

        contentArea.innerHTML = headerHTML + platosHTML;
    } catch (error) {
        contentArea.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">X</div>
                <p>Error al cargar el menú: ${error.message}</p>
            </div>
        `;
    }
}

// Cargar vista de clientes
async function loadClientes() {
    const contentArea = document.getElementById('contentArea');
    contentArea.innerHTML = '<div class="loading">Cargando clientes...</div>';

    try {
        const clientes = await API.getClientes();
        const user = getUser();
        const canEdit = user.roles.includes('ROLE_ADMIN') || user.roles.includes('ROLE_MOZO');

        const clientesRows = clientes.map(cliente => `
            <tr>
                <td>${cliente.dni}</td>
                <td>${cliente.nombres} ${cliente.apellidos}</td>
                <td>${cliente.telefono || 'N/A'}</td>
                <td>${cliente.correo || 'N/A'}</td>
                <td><span class="badge ${cliente.estado ? 'badge-success' : 'badge-danger'}">
                    ${cliente.estado ? 'Activo' : 'Inactivo'}
                </span></td>
                ${canEdit ? `
                <td>
                    <button class="btn btn-sm btn-primary" onclick="editCliente(${cliente.idCliente})">Editar</button>
                    ${user.roles.includes('ROLE_ADMIN') ? `<button class="btn btn-sm btn-danger" onclick="deleteCliente(${cliente.idCliente})">Eliminar</button>` : ''}
                </td>
                ` : ''}
            </tr>
        `).join('');

        contentArea.innerHTML = `
            <div class="table-container">
                <div class="table-header">
                    <h3>Clientes Registrados</h3>
                    ${canEdit ? '<button class="btn btn-success" onclick="showClienteModal()">Nuevo Cliente</button>' : ''}
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>DNI</th>
                            <th>Nombre</th>
                            <th>Teléfono</th>
                            <th>Correo</th>
                            <th>Estado</th>
                            ${canEdit ? '<th>Acciones</th>' : ''}
                        </tr>
                    </thead>
                    <tbody>
                        ${clientesRows.length > 0 ? clientesRows : '<tr><td colspan="6" style="text-align: center;">No hay clientes registrados</td></tr>'}
                    </tbody>
                </table>
            </div>
        `;
    } catch (error) {
        contentArea.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">X</div>
                <p>Error al cargar los clientes: ${error.message}</p>
            </div>
        `;
    }
}

// Cargar vista de insumos
async function loadInsumos() {
    const contentArea = document.getElementById('contentArea');
    contentArea.innerHTML = '<div class="loading">Cargando inventario...</div>';

    try {
        const insumos = await API.getInsumos();
        const user = getUser();
        const canEdit = user.roles.includes('ROLE_ADMIN');

        const insumosRows = insumos.map(insumo => {
            const stockBajo = insumo.stock <= insumo.stockMinimo;
            const estadoBadge = stockBajo ? 'badge-danger' : 'badge-success';

            return `
                <tr>
                    <td>${insumo.nombre}</td>
                    <td>${insumo.stock} ${insumo.unidadMedida}</td>
                    <td>${insumo.stockMinimo} ${insumo.unidadMedida}</td>
                    <td>S/ ${insumo.precioCompra.toFixed(2)}</td>
                    <td><span class="badge ${estadoBadge}">
                        ${stockBajo ? 'Stock Bajo' : 'OK'}
                    </span></td>
                    ${canEdit ? `
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="editInsumo(${insumo.idInsumo})">Editar</button>
                        <button class="btn btn-sm btn-danger" onclick="deleteInsumo(${insumo.idInsumo})">Eliminar</button>
                    </td>
                    ` : ''}
                </tr>
            `;
        }).join('');

        contentArea.innerHTML = `
            <div class="table-container">
                <div class="table-header">
                    <h3>Inventario de Insumos</h3>
                    ${canEdit ? '<button class="btn btn-success" onclick="showInsumoModal()">Nuevo Insumo</button>' : ''}
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>Insumo</th>
                            <th>Stock Actual</th>
                            <th>Stock Mínimo</th>
                            <th>Precio Compra</th>
                            <th>Estado</th>
                            ${canEdit ? '<th>Acciones</th>' : ''}
                        </tr>
                    </thead>
                    <tbody>
                        ${insumosRows.length > 0 ? insumosRows : '<tr><td colspan="6" style="text-align: center;">No hay insumos registrados</td></tr>'}
                    </tbody>
                </table>
            </div>
        `;
    } catch (error) {
        contentArea.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">X</div>
                <p>Error al cargar el inventario: ${error.message}</p>
            </div>
        `;
    }
}

// Cargar vista de facturas
async function loadFacturas() {
    const contentArea = document.getElementById('contentArea');
    contentArea.innerHTML = '<div class="loading">Cargando facturas...</div>';

    try {
        const facturas = await API.getFacturas();

        if (facturas.length === 0) {
            contentArea.innerHTML = `
                <div class="empty-state">
                    <div class="empty-state-icon">-</div>
                    <p>No hay facturas registradas</p>
                </div>
            `;
            return;
        }

        const facturasRows = facturas.map(factura => {
            const fecha = new Date(factura.fechaEmision).toLocaleString('es-ES');
            const metodoBadge = {
                'EFECTIVO': 'badge-success',
                'TARJETA': 'badge-info',
                'YAPE': 'badge-warning'
            }[factura.metodoPago] || 'badge-info';

            return `
                <tr>
                    <td>#${factura.idFactura}</td>
                    <td>${factura.pedido?.idPedido || 'N/A'}</td>
                    <td>${fecha}</td>
                    <td>S/ ${factura.total.toFixed(2)}</td>
                    <td><span class="badge ${metodoBadge}">${factura.metodoPago}</span></td>
                    <td><span class="badge ${factura.estado ? 'badge-success' : 'badge-danger'}">
                        ${factura.estado ? 'Activa' : 'Anulada'}
                    </span></td>
                </tr>
            `;
        }).join('');

        contentArea.innerHTML = `
            <div class="table-container">
                <div class="table-header">
                    <h3>Facturas Registradas</h3>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Pedido</th>
                            <th>Fecha Emisión</th>
                            <th>Total</th>
                            <th>Método Pago</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${facturasRows}
                    </tbody>
                </table>
            </div>
        `;
    } catch (error) {
        contentArea.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">X</div>
                <p>Error al cargar las facturas: ${error.message}</p>
            </div>
        `;
    }
}

// ==========================================
// FUNCIONES CRUD - CLIENTES
// ==========================================

function showClienteModal(clienteId = null) {
    const isEdit = clienteId !== null;
    const modalHTML = `
        <div class="modal active" id="clienteModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>${isEdit ? 'Editar' : 'Nuevo'} Cliente</h2>
                </div>
                <form id="clienteForm" onsubmit="return false;">
                    <div class="form-group">
                        <label for="clienteDni">DNI *</label>
                        <input type="text" id="clienteDni" required maxlength="8" pattern="[0-9]{8}">
                    </div>
                    <div class="form-group">
                        <label for="clienteNombres">Nombres *</label>
                        <input type="text" id="clienteNombres" required>
                    </div>
                    <div class="form-group">
                        <label for="clienteApellidos">Apellidos *</label>
                        <input type="text" id="clienteApellidos" required>
                    </div>
                    <div class="form-group">
                        <label for="clienteTelefono">Teléfono</label>
                        <input type="text" id="clienteTelefono" maxlength="15">
                    </div>
                    <div class="form-group">
                        <label for="clienteCorreo">Correo</label>
                        <input type="email" id="clienteCorreo">
                    </div>
                    <div class="form-group">
                        <label for="clienteDireccion">Dirección</label>
                        <input type="text" id="clienteDireccion">
                    </div>
                    <div class="form-group">
                        <label>
                            <input type="checkbox" id="clienteEstado" checked> Activo
                        </label>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="saveCliente(${clienteId})">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('beforeend', modalHTML);

    if (isEdit) {
        loadClienteData(clienteId);
    }
}

async function loadClienteData(id) {
    try {
        const cliente = await API.getCliente(id);
        document.getElementById('clienteDni').value = cliente.dni;
        document.getElementById('clienteNombres').value = cliente.nombres;
        document.getElementById('clienteApellidos').value = cliente.apellidos;
        document.getElementById('clienteTelefono').value = cliente.telefono || '';
        document.getElementById('clienteCorreo').value = cliente.correo || '';
        document.getElementById('clienteDireccion').value = cliente.direccion || '';
        document.getElementById('clienteEstado').checked = cliente.estado;
    } catch (error) {
        alert('Error al cargar datos del cliente: ' + error.message);
        closeModal();
    }
}

async function saveCliente(id) {
    const clienteData = {
        dni: document.getElementById('clienteDni').value,
        nombres: document.getElementById('clienteNombres').value,
        apellidos: document.getElementById('clienteApellidos').value,
        telefono: document.getElementById('clienteTelefono').value || null,
        correo: document.getElementById('clienteCorreo').value || null,
        direccion: document.getElementById('clienteDireccion').value || null,
        estado: document.getElementById('clienteEstado').checked
    };

    try {
        if (id) {
            await API.updateCliente(id, clienteData);
        } else {
            await API.createCliente(clienteData);
        }
        closeModal();
        loadClientes();
    } catch (error) {
        alert('Error al guardar cliente: ' + error.message);
    }
}

function editCliente(id) {
    showClienteModal(id);
}

async function deleteCliente(id) {
    if (confirm('¿Está seguro de eliminar este cliente?')) {
        try {
            await API.deleteCliente(id);
            loadClientes();
        } catch (error) {
            alert('Error al eliminar cliente: ' + error.message);
        }
    }
}

// ==========================================
// FUNCIONES CRUD - PLATOS
// ==========================================

function showPlatoModal(platoId = null) {
    const isEdit = platoId !== null;
    const modalHTML = `
        <div class="modal active" id="platoModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>${isEdit ? 'Editar' : 'Nuevo'} Plato</h2>
                </div>
                <form id="platoForm" onsubmit="return false;">
                    <div class="form-group">
                        <label for="platoNombre">Nombre *</label>
                        <input type="text" id="platoNombre" required>
                    </div>
                    <div class="form-group">
                        <label for="platoDescripcion">Descripción</label>
                        <textarea id="platoDescripcion" rows="3"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="platoTipo">Tipo *</label>
                        <select id="platoTipo" required>
                            <option value="ENTRADA">Entrada</option>
                            <option value="FONDO">Fondo</option>
                            <option value="POSTRE">Postre</option>
                            <option value="BEBIDA">Bebida</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="platoPrecio">Precio *</label>
                        <input type="number" id="platoPrecio" step="0.01" min="0" required>
                    </div>
                    <div class="form-group">
                        <label>
                            <input type="checkbox" id="platoEstado" checked> Disponible
                        </label>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="savePlato(${platoId})">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('beforeend', modalHTML);

    if (isEdit) {
        loadPlatoData(platoId);
    }
}

async function loadPlatoData(id) {
    try {
        const plato = await API.getPlato(id);
        document.getElementById('platoNombre').value = plato.nombre;
        document.getElementById('platoDescripcion').value = plato.descripcion || '';
        document.getElementById('platoTipo').value = plato.tipo;
        document.getElementById('platoPrecio').value = plato.precio;
        document.getElementById('platoEstado').checked = plato.estado;
    } catch (error) {
        alert('Error al cargar datos del plato: ' + error.message);
        closeModal();
    }
}

async function savePlato(id) {
    const platoData = {
        nombre: document.getElementById('platoNombre').value,
        descripcion: document.getElementById('platoDescripcion').value || null,
        tipo: document.getElementById('platoTipo').value,
        precio: parseFloat(document.getElementById('platoPrecio').value),
        estado: document.getElementById('platoEstado').checked
    };

    try {
        if (id) {
            await API.updatePlato(id, platoData);
        } else {
            await API.createPlato(platoData);
        }
        closeModal();
        loadPlatos();
    } catch (error) {
        alert('Error al guardar plato: ' + error.message);
    }
}

function editPlato(id) {
    showPlatoModal(id);
}

async function deletePlato(id) {
    if (confirm('¿Está seguro de eliminar este plato?')) {
        try {
            await API.deletePlato(id);
            loadPlatos();
        } catch (error) {
            alert('Error al eliminar plato: ' + error.message);
        }
    }
}

// ==========================================
// FUNCIONES CRUD - INSUMOS
// ==========================================

function showInsumoModal(insumoId = null) {
    const isEdit = insumoId !== null;
    const modalHTML = `
        <div class="modal active" id="insumoModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>${isEdit ? 'Editar' : 'Nuevo'} Insumo</h2>
                </div>
                <form id="insumoForm" onsubmit="return false;">
                    <div class="form-group">
                        <label for="insumoNombre">Nombre *</label>
                        <input type="text" id="insumoNombre" required>
                    </div>
                    <div class="form-group">
                        <label for="insumoStock">Stock Actual *</label>
                        <input type="number" id="insumoStock" step="0.01" min="0" required>
                    </div>
                    <div class="form-group">
                        <label for="insumoStockMinimo">Stock Mínimo *</label>
                        <input type="number" id="insumoStockMinimo" step="0.01" min="0" required>
                    </div>
                    <div class="form-group">
                        <label for="insumoUnidadMedida">Unidad de Medida *</label>
                        <select id="insumoUnidadMedida" required>
                            <option value="kg">Kilogramos (kg)</option>
                            <option value="g">Gramos (g)</option>
                            <option value="l">Litros (l)</option>
                            <option value="ml">Mililitros (ml)</option>
                            <option value="unidad">Unidad</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="insumoPrecioCompra">Precio de Compra *</label>
                        <input type="number" id="insumoPrecioCompra" step="0.01" min="0" required>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="saveInsumo(${insumoId})">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('beforeend', modalHTML);

    if (isEdit) {
        loadInsumoData(insumoId);
    }
}

async function loadInsumoData(id) {
    try {
        const insumo = await API.getInsumo(id);
        document.getElementById('insumoNombre').value = insumo.nombre;
        document.getElementById('insumoStock').value = insumo.stock;
        document.getElementById('insumoStockMinimo').value = insumo.stockMinimo;
        document.getElementById('insumoUnidadMedida').value = insumo.unidadMedida;
        document.getElementById('insumoPrecioCompra').value = insumo.precioCompra;
    } catch (error) {
        alert('Error al cargar datos del insumo: ' + error.message);
        closeModal();
    }
}

async function saveInsumo(id) {
    const insumoData = {
        nombre: document.getElementById('insumoNombre').value,
        stock: parseFloat(document.getElementById('insumoStock').value),
        stockMinimo: parseFloat(document.getElementById('insumoStockMinimo').value),
        unidadMedida: document.getElementById('insumoUnidadMedida').value,
        precioCompra: parseFloat(document.getElementById('insumoPrecioCompra').value)
    };

    try {
        if (id) {
            await API.updateInsumo(id, insumoData);
        } else {
            await API.createInsumo(insumoData);
        }
        closeModal();
        loadInsumos();
    } catch (error) {
        alert('Error al guardar insumo: ' + error.message);
    }
}

function editInsumo(id) {
    showInsumoModal(id);
}

async function deleteInsumo(id) {
    if (confirm('¿Está seguro de eliminar este insumo?')) {
        try {
            await API.deleteInsumo(id);
            loadInsumos();
        } catch (error) {
            alert('Error al eliminar insumo: ' + error.message);
        }
    }
}

// ==========================================
// FUNCIONES - PEDIDOS
// ==========================================

let pedidoDetalles = [];

async function showPedidoModal() {
    pedidoDetalles = [];

    try {
        const [mesas, clientes, platos] = await Promise.all([
            API.getMesas(),
            API.getClientes(),
            API.getPlatos()
        ]);

        console.log('Platos recibidos:', platos);

        const mesasDisponibles = mesas.filter(m => m.estado === 'DISPONIBLE');
        // Filtrar solo los platos disponibles (estado = true)
        const platosDisponibles = platos.filter(p => p.estado === true);

        const modalHTML = `
            <div class="modal active" id="pedidoModal">
                <div class="modal-content" style="max-width: 800px;">
                    <div class="modal-header">
                        <h2>Nuevo Pedido</h2>
                    </div>
                    <form id="pedidoForm" onsubmit="return false;">
                        <div class="form-group">
                            <label for="pedidoMesa">Mesa *</label>
                            <select id="pedidoMesa" required>
                                <option value="">Seleccione una mesa</option>
                                ${mesasDisponibles.map(m => `<option value="${m.idMesa}">Mesa ${m.numero} (${m.capacidad} personas)</option>`).join('')}
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="pedidoCliente">Cliente *</label>
                            <select id="pedidoCliente" required>
                                <option value="">Seleccione un cliente</option>
                                ${clientes.filter(c => c.estado).map(c => `<option value="${c.idCliente}">${c.nombres} ${c.apellidos}</option>`).join('')}
                            </select>
                        </div>
                        <hr>
                        <h3>Agregar Platos</h3>
                        <div class="form-group">
                            <label for="selectPlato">Plato</label>
                            <select id="selectPlato">
                                <option value="">Seleccione un plato</option>
                                ${platosDisponibles.map(p => `<option value="${p.idPlato}" data-precio="${p.precio}">${p.nombre} - S/ ${p.precio.toFixed(2)}</option>`).join('')}
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="cantidadPlato">Cantidad</label>
                            <input type="number" id="cantidadPlato" min="1" value="1">
                        </div>
                        <button type="button" class="btn btn-primary" onclick="agregarPlatoDetalle()">Agregar Plato</button>

                        <div id="detallesPedido" style="margin-top: 20px;">
                            <h4>Detalles del Pedido</h4>
                            <table style="width: 100%; margin-top: 10px;">
                                <thead>
                                    <tr>
                                        <th>Plato</th>
                                        <th>Precio</th>
                                        <th>Cantidad</th>
                                        <th>Subtotal</th>
                                        <th>Acción</th>
                                    </tr>
                                </thead>
                                <tbody id="detallesBody">
                                    <tr><td colspan="5" style="text-align: center;">No hay platos agregados</td></tr>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="3"><strong>Total:</strong></td>
                                        <td colspan="2"><strong id="totalPedido">S/ 0.00</strong></td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
                            <button type="button" class="btn btn-primary" onclick="savePedido()">Crear Pedido</button>
                        </div>
                    </form>
                </div>
            </div>
        `;

        document.body.insertAdjacentHTML('beforeend', modalHTML);
    } catch (error) {
        alert('Error al cargar datos: ' + error.message);
    }
}

function agregarPlatoDetalle() {
    const selectPlato = document.getElementById('selectPlato');
    const cantidad = parseInt(document.getElementById('cantidadPlato').value);

    if (!selectPlato.value || cantidad <= 0) {
        alert('Seleccione un plato y una cantidad válida');
        return;
    }

    const platoId = parseInt(selectPlato.value);
    const platoNombre = selectPlato.options[selectPlato.selectedIndex].text.split(' - ')[0];
    const precio = parseFloat(selectPlato.options[selectPlato.selectedIndex].dataset.precio);

    // Verificar si ya existe el plato
    const existente = pedidoDetalles.find(d => d.platoId === platoId);
    if (existente) {
        existente.cantidad += cantidad;
    } else {
        pedidoDetalles.push({ platoId, platoNombre, precio, cantidad });
    }

    actualizarDetallesPedido();
    selectPlato.value = '';
    document.getElementById('cantidadPlato').value = 1;
}

function quitarPlatoDetalle(index) {
    pedidoDetalles.splice(index, 1);
    actualizarDetallesPedido();
}

function actualizarDetallesPedido() {
    const tbody = document.getElementById('detallesBody');

    if (pedidoDetalles.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No hay platos agregados</td></tr>';
        document.getElementById('totalPedido').textContent = 'S/ 0.00';
        return;
    }

    const rows = pedidoDetalles.map((detalle, index) => {
        const subtotal = detalle.precio * detalle.cantidad;
        return `
            <tr>
                <td>${detalle.platoNombre}</td>
                <td>S/ ${detalle.precio.toFixed(2)}</td>
                <td>${detalle.cantidad}</td>
                <td>S/ ${subtotal.toFixed(2)}</td>
                <td><button class="btn btn-sm btn-danger" onclick="quitarPlatoDetalle(${index})">Quitar</button></td>
            </tr>
        `;
    }).join('');

    tbody.innerHTML = rows;

    const total = pedidoDetalles.reduce((sum, d) => sum + (d.precio * d.cantidad), 0);
    document.getElementById('totalPedido').textContent = `S/ ${total.toFixed(2)}`;
}

async function savePedido() {
    const mesaId = document.getElementById('pedidoMesa').value;
    const clienteId = document.getElementById('pedidoCliente').value;

    if (!mesaId || !clienteId) {
        alert('Seleccione una mesa y un cliente');
        return;
    }

    if (pedidoDetalles.length === 0) {
        alert('Agregue al menos un plato al pedido');
        return;
    }

    const pedidoData = {
        mesa: { idMesa: parseInt(mesaId) },
        cliente: { idCliente: parseInt(clienteId) },
        estado: 'PENDIENTE',
        detalles: pedidoDetalles.map(d => ({
            plato: { idPlato: d.platoId },
            cantidad: d.cantidad,
            subtotal: parseFloat((d.precio * d.cantidad).toFixed(2))
        }))
    };

    console.log('Datos del pedido a enviar:', JSON.stringify(pedidoData, null, 2));

    try {
        await API.createPedido(pedidoData);

        // El backend ya cambia el estado de la mesa automáticamente
        closeModal();
        loadPedidos();
        loadDashboardStats();
    } catch (error) {
        alert('Error al crear pedido: ' + error.message);
    }
}

async function cambiarEstadoPedido(pedidoId, nuevoEstado) {
    try {
        // Usar endpoint específico de cambio de estado
        await apiRequest(`/pedidos/${pedidoId}/estado?estado=${nuevoEstado}`, 'PATCH');
        loadPedidos();
        loadDashboardStats();
    } catch (error) {
        alert('Error al cambiar estado: ' + error.message);
    }
}

async function cerrarPedidoConFactura(pedidoId) {
    const modalHTML = `
        <div class="modal active" id="facturaModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>Generar Factura</h2>
                </div>
                <form id="facturaForm" onsubmit="return false;">
                    <div class="form-group">
                        <label for="metodoPago">Método de Pago *</label>
                        <select id="metodoPago" required>
                            <option value="EFECTIVO">Efectivo</option>
                            <option value="TARJETA">Tarjeta</option>
                            <option value="YAPE">Yape</option>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="generarFactura(${pedidoId})">Generar</button>
                    </div>
                </form>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('beforeend', modalHTML);
}

async function generarFactura(pedidoId) {
    const metodoPago = document.getElementById('metodoPago').value;

    try {
        // Crear factura directamente con los parámetros que espera el backend
        // El backend ya se encarga de cerrar el pedido y liberar la mesa
        await API.createFactura(pedidoId, metodoPago);

        closeModal();
        loadPedidos();
        loadDashboardStats();
        alert('Factura generada exitosamente');
    } catch (error) {
        alert('Error al generar factura: ' + error.message);
    }
}

// ==========================================
// FUNCIONES - MESAS
// ==========================================

async function cambiarEstadoMesa(mesaId, nuevoEstado) {
    try {
        // Usar endpoint específico de cambio de estado
        await apiRequest(`/mesas/${mesaId}/estado?estado=${nuevoEstado}`, 'PATCH');
        loadMesas();
        loadDashboardStats();
    } catch (error) {
        alert('Error al cambiar estado de mesa: ' + error.message);
    }
}

// ==========================================
// UTILIDADES
// ==========================================

function closeModal() {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => modal.remove());
}
