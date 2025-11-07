<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Administradores</title>
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
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/AlumnoController">Alumnos</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/CursoController">Cursos</a></li>
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/AdministradorController">Administradores</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>Gestión de Administradores</h2>
        <a href="AdministradorController?accion=nuevo" class="btn btn-primary mb-3">Nuevo Administrador</a>

        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>Código</th>
                    <th>Usuario</th>
                    <th>Nombre</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="admin" items="${administradores}">
                    <c:if test="${admin.estado == 1}">
                        <tr>
                            <td>${admin.codigo}</td>
                            <td>${admin.usuario}</td>
                            <td>${admin.nombre}</td>
                            <td><span class="badge bg-success">Activo</span></td>
                            <td>
                                <a href="AdministradorController?accion=editar&codigo=${admin.codigo}" class="btn btn-sm btn-warning">Editar</a>
                                <a href="AdministradorController?accion=eliminar&codigo=${admin.codigo}" class="btn btn-sm btn-danger" onclick="return confirm('¿Está seguro de eliminar este administrador?')">Eliminar</a>
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
