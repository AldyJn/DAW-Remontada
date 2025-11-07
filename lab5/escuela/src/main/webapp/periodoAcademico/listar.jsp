<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Periodos Academicos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/AlumnoController">Alumnos</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/CursoController">Cursos</a></li>
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/PeriodoAcademicoController">Periodos</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/MatriculaController">Matriculas</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>Periodos Academicos</h2>
        <a href="PeriodoAcademicoController?accion=nuevo" class="btn btn-primary mb-3">Nuevo Periodo</a>

        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Fin</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="periodo" items="${periodos}">
                    <tr>
                        <td>${periodo.idPeriodo}</td>
                        <td>${periodo.nombrePeriodo}</td>
                        <td><fmt:formatDate value="${periodo.fechaInicio}" pattern="dd/MM/yyyy"/></td>
                        <td><fmt:formatDate value="${periodo.fechaFin}" pattern="dd/MM/yyyy"/></td>
                        <td><span class="badge bg-success">Activo</span></td>
                        <td>
                            <a href="PeriodoAcademicoController?accion=editar&id=${periodo.idPeriodo}" class="btn btn-sm btn-warning">Editar</a>
                            <a href="PeriodoAcademicoController?accion=eliminar&id=${periodo.idPeriodo}" class="btn btn-sm btn-danger" onclick="return confirm('Esta seguro?')">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
