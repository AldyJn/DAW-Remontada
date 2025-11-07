<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${alumno != null ? 'Editar' : 'Nuevo'} Alumno</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>${alumno != null ? 'Editar' : 'Nuevo'} Alumno</h2>

        <c:if test="${error != null}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>

        <form action="AlumnoController" method="post">
            <input type="hidden" name="accion" value="${alumno != null ? 'actualizar' : 'guardar'}">

            <div class="mb-3">
                <label for="codigo" class="form-label">Código</label>
                <input type="text" class="form-control" id="codigo" name="codigo" value="${alumno.codigo}" ${alumno != null ? 'readonly' : ''} required>
            </div>

            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="${alumno.nombre}" required>
            </div>

            <div class="mb-3">
                <label for="apellido" class="form-label">Apellido</label>
                <input type="text" class="form-control" id="apellido" name="apellido" value="${alumno.apellido}" required>
            </div>

            <div class="mb-3">
                <label for="correo" class="form-label">Correo</label>
                <input type="email" class="form-control" id="correo" name="correo" value="${alumno.correo}" required>
            </div>

            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="AlumnoController?accion=listar" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
