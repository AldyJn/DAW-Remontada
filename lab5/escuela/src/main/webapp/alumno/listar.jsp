<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Alumnos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/AlumnoController">Alumnos</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/CursoController">Cursos</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/AdministradorController">Administradores</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>Gestión de Alumnos</h2>
        <a href="AlumnoController?accion=nuevo" class="btn btn-primary mb-3">Nuevo Alumno</a>

        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Correo</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="alumno" items="${alumnos}">
                    <c:if test="${alumno.estado == 1}">
                        <tr>
                            <td>${alumno.codigo}</td>
                            <td>${alumno.nombre}</td>
                            <td>${alumno.apellido}</td>
                            <td>${alumno.correo}</td>
                            <td><span class="badge bg-success">Activo</span></td>
                            <td>
                                <a href="AlumnoController?accion=editar&codigo=${alumno.codigo}" class="btn btn-sm btn-warning">Editar</a>
                                <a href="AlumnoController?accion=eliminar&codigo=${alumno.codigo}" class="btn btn-sm btn-danger" onclick="return confirm('¿Está seguro de eliminar este alumno?')">Eliminar</a>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
