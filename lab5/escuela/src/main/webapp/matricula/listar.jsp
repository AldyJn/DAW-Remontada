<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Matrículas</title>
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
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/PeriodoAcademicoController">Periodos</a></li>
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/MatriculaController">Matrículas</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>Gestión de Matrículas</h2>
        <a href="MatriculaController?accion=nuevo" class="btn btn-primary mb-3">Nueva Matrícula</a>

        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Código Alumno</th>
                    <th>ID Periodo</th>
                    <th>Fecha Matrícula</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="matricula" items="${matriculas}">
                    <tr>
                        <td>${matricula.idMatricula}</td>
                        <td>${matricula.idAlumno}</td>
                        <td>${matricula.idPeriodo}</td>
                        <td><fmt:formatDate value="${matricula.fechaMatricula}" pattern="dd/MM/yyyy"/></td>
                        <td><span class="badge bg-success">Activa</span></td>
                        <td>
                            <a href="MatriculaController?accion=retirar&id=${matricula.idMatricula}" class="btn btn-sm btn-danger" onclick="return confirm('¿Retirar matrícula?')">Retirar</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
