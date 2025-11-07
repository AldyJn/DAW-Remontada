<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Asistencia</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .main-card {
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
        }
        .step-indicator {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
        }
        .tabla-asistencia {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        .tabla-asistencia th {
            background: #28a745;
            color: white;
            font-weight: 600;
        }
        .select-asistencia {
            max-width: 150px;
        }
        .btn-cargar {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            border: none;
            padding: 12px 30px;
            font-weight: 600;
        }
        .btn-cargar:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(40, 167, 69, 0.4);
        }
        .badge-presente { background-color: #28a745; }
        .badge-ausente { background-color: #dc3545; }
        .badge-tardanza { background-color: #ffc107; color: #000; }
        .badge-justificada { background-color: #17a2b8; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-success">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="AsistenciaController?accion=listar">Ver Asistencias</a>
            </div>
        </div>
    </nav>

    <div class="container mt-5 mb-5">
        <h2 class="text-center mb-4">Registro de Asistencia</h2>

        <c:if test="${error != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error:</strong> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="card main-card">
            <div class="card-body p-4">
                <!-- Paso 1: Seleccion de Periodo, Curso y Semana -->
                <div class="step-indicator">
                    <h5 class="mb-3">Paso 1: Seleccione el Periodo, Curso y Semana</h5>
                    <p class="mb-0">Seleccione los filtros necesarios para cargar la lista de alumnos</p>
                </div>

                <form method="post" action="AsistenciaController" id="formFiltros">
                    <input type="hidden" name="accion" value="cargarAlumnos">

                    <div class="row g-3">
                        <div class="col-md-4">
                            <label for="idPeriodo" class="form-label fw-bold">Periodo Academico</label>
                            <select class="form-select form-select-lg" id="idPeriodo" name="idPeriodo" required>
                                <option value="">-- Seleccione --</option>
                                <c:forEach var="periodo" items="${periodos}">
                                    <option value="${periodo.id_periodo}"
                                            ${periodo.id_periodo == idPeriodoSeleccionado ? 'selected' : ''}>
                                        ${periodo.nombre_periodo}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-4">
                            <label for="idCurso" class="form-label fw-bold">Curso</label>
                            <select class="form-select form-select-lg" id="idCurso" name="idCurso" required>
                                <option value="">-- Seleccione --</option>
                                <c:forEach var="curso" items="${cursos}">
                                    <option value="${curso.codigo}"
                                            ${curso.codigo == idCursoSeleccionado ? 'selected' : ''}>
                                        ${curso.nombre}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-4">
                            <label for="semana" class="form-label fw-bold">Semana</label>
                            <select class="form-select form-select-lg" id="semana" name="semana" required>
                                <option value="">-- Seleccione --</option>
                                <c:forEach begin="1" end="16" var="sem">
                                    <option value="${sem}" ${sem == semanaSeleccionada ? 'selected' : ''}>
                                        Semana ${sem}
                                    </option>
                                </c:forEach>
                            </select>
                            <small class="form-text text-muted">
                                Seleccione la semana de asistencia (1-16)
                            </small>
                        </div>
                    </div>

                    <div class="text-center mt-4">
                        <button type="submit" class="btn btn-success btn-lg btn-cargar">
                            Cargar Lista de Alumnos
                        </button>
                    </div>
                </form>

                <!-- Paso 2: Tabla de alumnos para marcar asistencia -->
                <c:if test="${not empty alumnos}">
                    <hr class="my-5">

                    <div class="step-indicator">
                        <h5 class="mb-3">Paso 2: Marque la Asistencia de los Alumnos</h5>
                        <p class="mb-0">Seleccione el estado de asistencia para cada alumno</p>
                    </div>

                    <form method="post" action="AsistenciaController" id="formAsistencia">
                        <input type="hidden" name="accion" value="guardarMasivo">
                        <input type="hidden" name="idPeriodo" value="${idPeriodoSeleccionado}">
                        <input type="hidden" name="idCurso" value="${idCursoSeleccionado}">
                        <input type="hidden" name="semana" value="${semanaSeleccionada}">

                        <div class="tabla-asistencia">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th width="8%">#</th>
                                            <th width="12%">Código</th>
                                            <th width="45%">Nombre del Alumno</th>
                                            <th width="35%" class="text-center">Estado de Asistencia</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="alumno" items="${alumnos}" varStatus="status">
                                            <tr>
                                                <td class="text-center"><strong>${status.count}</strong></td>
                                                <td>
                                                    ${alumno.codigo_alumno}
                                                    <input type="hidden" name="idDetalle" value="${alumno.id_detalle}">
                                                    <input type="hidden" name="idAsistencia" value="${alumno.id_asistencia}">
                                                </td>
                                                <td><strong>${alumno.nombre_alumno}</strong></td>
                                                <td class="text-center">
                                                    <select class="form-select form-select-lg select-asistencia mx-auto"
                                                            name="estado">
                                                        <option value="">-- Seleccione --</option>
                                                        <option value="P"
                                                                ${alumno.estado_actual == 'P' ? 'selected' : ''}>
                                                            Presente
                                                        </option>
                                                        <option value="A"
                                                                ${alumno.estado_actual == 'A' ? 'selected' : ''}>
                                                            Ausente
                                                        </option>
                                                        <option value="T"
                                                                ${alumno.estado_actual == 'T' ? 'selected' : ''}>
                                                            Tardanza
                                                        </option>
                                                        <option value="J"
                                                                ${alumno.estado_actual == 'J' ? 'selected' : ''}>
                                                            Justificada
                                                        </option>
                                                    </select>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="d-flex justify-content-between align-items-center mt-4">
                                <div class="text-muted">
                                    <strong>Total de alumnos:</strong> ${alumnos.size()}
                                    <div class="mt-2">
                                        <span class="badge badge-presente me-2">P = Presente</span>
                                        <span class="badge badge-ausente me-2">A = Ausente</span>
                                        <span class="badge badge-tardanza me-2">T = Tardanza</span>
                                        <span class="badge badge-justificada">J = Justificada</span>
                                    </div>
                                </div>
                                <div>
                                    <a href="AsistenciaController?accion=registrar" class="btn btn-secondary btn-lg me-2">
                                        Limpiar Filtros
                                    </a>
                                    <button type="submit" class="btn btn-success btn-lg">
                                        Guardar Todas las Asistencias
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </c:if>

                <c:if test="${empty alumnos && idCursoSeleccionado != null}">
                    <div class="alert alert-warning mt-4 text-center">
                        <h5>No hay alumnos matriculados</h5>
                        <p class="mb-0">No se encontraron alumnos matriculados en el curso seleccionado para este periodo.</p>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Validación del formulario de asistencia
        const formAsistencia = document.getElementById('formAsistencia');
        if(formAsistencia) {
            formAsistencia.addEventListener('submit', function(e) {
                let hayAsistencias = false;
                const selects = document.querySelectorAll('select[name="estado"]');

                selects.forEach(select => {
                    if(select.value && select.value.trim() !== '') {
                        hayAsistencias = true;
                    }
                });

                if(!hayAsistencias) {
                    e.preventDefault();
                    alert('Debe marcar al menos una asistencia antes de guardar.');
                    return false;
                }
            });
        }

        // Atajos de teclado para selección rápida
        document.addEventListener('keydown', function(e) {
            const activeElement = document.activeElement;
            if(activeElement && activeElement.tagName === 'SELECT' && activeElement.name === 'estado') {
                switch(e.key.toUpperCase()) {
                    case 'P':
                        activeElement.value = 'P';
                        e.preventDefault();
                        break;
                    case 'A':
                        activeElement.value = 'A';
                        e.preventDefault();
                        break;
                    case 'T':
                        activeElement.value = 'T';
                        e.preventDefault();
                        break;
                    case 'J':
                        activeElement.value = 'J';
                        e.preventDefault();
                        break;
                }
            }
        });

        // Colorear las filas según el estado seleccionado
        document.querySelectorAll('select[name="estado"]').forEach(select => {
            select.addEventListener('change', function() {
                const row = this.closest('tr');
                row.classList.remove('table-success', 'table-danger', 'table-warning', 'table-info');

                switch(this.value) {
                    case 'P':
                        row.classList.add('table-success');
                        break;
                    case 'A':
                        row.classList.add('table-danger');
                        break;
                    case 'T':
                        row.classList.add('table-warning');
                        break;
                    case 'J':
                        row.classList.add('table-info');
                        break;
                }
            });

            // Aplicar color inicial si ya hay un estado seleccionado
            if(select.value) {
                select.dispatchEvent(new Event('change'));
            }
        });
    </script>
</body>
</html>
