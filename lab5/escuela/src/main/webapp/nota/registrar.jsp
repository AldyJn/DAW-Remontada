<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Notas</title>
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
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
        }
        .tabla-notas {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        .tabla-notas th {
            background: #667eea;
            color: white;
            font-weight: 600;
        }
        .input-nota {
            max-width: 100px;
        }
        .btn-cargar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            padding: 12px 30px;
            font-weight: 600;
        }
        .btn-cargar:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="NotaController?accion=listar">Ver Notas</a>
            </div>
        </div>
    </nav>

    <div class="container mt-5 mb-5">
        <h2 class="text-center mb-4">Registro de Notas</h2>

        <c:if test="${error != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error:</strong> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="card main-card">
            <div class="card-body p-4">
                <!-- Paso 1: Seleccion de Periodo, Curso y Evaluacion -->
                <div class="step-indicator">
                    <h5 class="mb-3">Paso 1: Seleccione el Periodo, Curso y Evaluacion</h5>
                    <p class="mb-0">Seleccione los filtros necesarios para cargar la lista de alumnos</p>
                </div>

                <form method="post" action="NotaController" id="formFiltros">
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
                                            data-codigo="${curso.codigo}"
                                            ${curso.codigo == idCursoSeleccionado ? 'selected' : ''}>
                                        ${curso.nombre}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-4">
                            <label for="idEvaluacion" class="form-label fw-bold">Tipo de Evaluacion</label>
                            <select class="form-select form-select-lg" id="idEvaluacion" name="idEvaluacion" required
                                    ${empty idCursoSeleccionado ? 'disabled' : ''}>
                                <option value="">-- Primero seleccione un curso --</option>
                                <c:forEach var="eval" items="${evaluaciones}">
                                    <option value="${eval.id_evaluacion}"
                                            ${eval.id_evaluacion == idEvaluacionSeleccionada ? 'selected' : ''}>
                                        ${eval.nombre} (${eval.peso * 100}%)
                                    </option>
                                </c:forEach>
                            </select>
                            <small class="form-text text-muted">
                                Las evaluaciones se cargaran al seleccionar el curso
                            </small>
                        </div>
                    </div>

                    <div class="text-center mt-4">
                        <button type="submit" class="btn btn-primary btn-lg btn-cargar">
                            Cargar Lista de Alumnos
                        </button>
                    </div>
                </form>

                <!-- Paso 2: Tabla de alumnos para ingresar notas -->
                <c:if test="${not empty alumnos}">
                    <hr class="my-5">

                    <div class="step-indicator">
                        <h5 class="mb-3">Paso 2: Ingrese las Notas de los Alumnos</h5>
                        <p class="mb-0">Complete las notas para cada alumno en la tabla inferior</p>
                    </div>

                    <form method="post" action="NotaController" id="formNotas">
                        <input type="hidden" name="accion" value="guardarMasivo">
                        <input type="hidden" name="idEvaluacion" value="${idEvaluacionSeleccionada}">

                        <div class="tabla-notas">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr>
                                            <th width="10%">#</th>
                                            <th width="15%">Código</th>
                                            <th width="50%">Nombre del Alumno</th>
                                            <th width="25%" class="text-center">Nota (0-20)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="alumno" items="${alumnos}" varStatus="status">
                                            <tr>
                                                <td class="text-center"><strong>${status.count}</strong></td>
                                                <td>
                                                    ${alumno.codigo_alumno}
                                                    <input type="hidden" name="idDetalle" value="${alumno.id_detalle}">
                                                    <input type="hidden" name="idNota" value="${alumno.id_nota}">
                                                </td>
                                                <td><strong>${alumno.nombre_alumno}</strong></td>
                                                <td class="text-center">
                                                    <input type="number"
                                                           class="form-control form-control-lg input-nota mx-auto text-center"
                                                           name="nota"
                                                           value="${alumno.nota_actual > 0 ? alumno.nota_actual : ''}"
                                                           step="0.01"
                                                           min="0"
                                                           max="20"
                                                           placeholder="0.00">
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="d-flex justify-content-between align-items-center mt-4">
                                <div class="text-muted">
                                    <strong>Total de alumnos:</strong> ${alumnos.size()}
                                </div>
                                <div>
                                    <a href="NotaController?accion=registrar" class="btn btn-secondary btn-lg me-2">
                                        Limpiar Filtros
                                    </a>
                                    <button type="submit" class="btn btn-success btn-lg">
                                        Guardar Todas las Notas
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
        // Datos de todas las evaluaciones
        const todasLasEvaluaciones = [
            <c:forEach var="eval" items="${todasEvaluaciones}" varStatus="status">
                {
                    id: ${eval.id_evaluacion},
                    idCurso: '${eval.id_curso}',
                    nombre: '${eval.nombre}',
                    peso: ${eval.peso}
                }${!status.last ? ',' : ''}
            </c:forEach>
        ];

        // Filtrar evaluaciones cuando se selecciona un curso
        const selectCurso = document.getElementById('idCurso');
        const selectEval = document.getElementById('idEvaluacion');

        if(selectCurso && selectEval) {
            selectCurso.addEventListener('change', function() {
                const cursoSeleccionado = this.value;

                // Limpiar select
                selectEval.innerHTML = '<option value="">-- Seleccione una evaluación --</option>';

                if(cursoSeleccionado) {
                    // Filtrar evaluaciones del curso
                    const evaluacionesFiltradas = todasLasEvaluaciones.filter(e => e.idCurso === cursoSeleccionado);

                    if(evaluacionesFiltradas.length > 0) {
                        evaluacionesFiltradas.forEach(eval => {
                            const option = document.createElement('option');
                            option.value = eval.id;
                            option.textContent = eval.nombre + ' (' + (eval.peso * 100) + '%)';
                            selectEval.appendChild(option);
                        });
                        selectEval.disabled = false;
                    } else {
                        selectEval.innerHTML = '<option value="">-- No hay evaluaciones disponibles --</option>';
                        selectEval.disabled = true;
                    }
                } else {
                    selectEval.innerHTML = '<option value="">-- Primero seleccione un curso --</option>';
                    selectEval.disabled = true;
                }
            });

            // Cargar evaluaciones si ya hay un curso seleccionado (después de POST)
            if(selectCurso.value) {
                selectCurso.dispatchEvent(new Event('change'));
            }
        }

        // Validación del formulario de notas
        const formNotas = document.getElementById('formNotas');
        if(formNotas) {
            formNotas.addEventListener('submit', function(e) {
                let hayNotas = false;
                const inputs = document.querySelectorAll('input[name="nota"]');

                inputs.forEach(input => {
                    if(input.value && input.value.trim() !== '') {
                        hayNotas = true;
                    }
                });

                if(!hayNotas) {
                    e.preventDefault();
                    alert('Debe ingresar al menos una nota antes de guardar.');
                    return false;
                }
            });
        }
    </script>
</body>
</html>
