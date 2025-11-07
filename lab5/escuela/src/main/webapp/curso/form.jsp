<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${curso != null ? 'Editar' : 'Nuevo'} Curso</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>${curso != null ? 'Editar' : 'Nuevo'} Curso</h2>

        <c:if test="${error != null}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>

        <form action="CursoController" method="post">
            <input type="hidden" name="accion" value="${curso != null ? 'actualizar' : 'guardar'}">

            <div class="mb-3">
                <label for="codigo" class="form-label">Código</label>
                <input type="text" class="form-control" id="codigo" name="codigo" value="${curso.codigo}" ${curso != null ? 'readonly' : ''} required>
            </div>

            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="${curso.nombre}" required>
            </div>

            <div class="mb-3">
                <label for="creditos" class="form-label">Créditos</label>
                <input type="number" class="form-control" id="creditos" name="creditos" value="${curso.creditos}" min="1" max="6" required>
            </div>

            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="CursoController?accion=listar" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
