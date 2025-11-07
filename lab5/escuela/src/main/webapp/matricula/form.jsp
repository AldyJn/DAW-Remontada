<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nueva Matrícula</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>Nueva Matrícula</h2>

        <c:if test="${error != null}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>

        <form action="MatriculaController" method="post">
            <input type="hidden" name="accion" value="guardar">

            <div class="mb-3">
                <label for="idAlumno" class="form-label">Alumno</label>
                <select class="form-select" id="idAlumno" name="idAlumno" required>
                    <option value="">Seleccione un alumno</option>
                    <c:forEach var="alumno" items="${alumnos}">
                        <option value="${alumno.codigo}">${alumno.codigo} - ${alumno.nombre} ${alumno.apellido}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label for="idPeriodo" class="form-label">Periodo Académico</label>
                <select class="form-select" id="idPeriodo" name="idPeriodo" required>
                    <option value="">Seleccione un periodo</option>
                    <c:forEach var="periodo" items="${periodos}">
                        <option value="${periodo.idPeriodo}">${periodo.nombrePeriodo}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label for="fechaMatricula" class="form-label">Fecha de Matrícula</label>
                <input type="date" class="form-control" id="fechaMatricula" name="fechaMatricula" required>
            </div>

            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="MatriculaController?accion=listar" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
