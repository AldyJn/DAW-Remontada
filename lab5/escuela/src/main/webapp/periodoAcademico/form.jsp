<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${periodo != null ? 'Editar' : 'Nuevo'} Periodo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>${periodo != null ? 'Editar' : 'Nuevo'} Periodo Academico</h2>

        <c:if test="${error != null}">
            <div class="alert alert-danger" role="alert">${error}</div>
        </c:if>

        <form action="PeriodoAcademicoController" method="post">
            <input type="hidden" name="accion" value="${periodo != null ? 'actualizar' : 'guardar'}">
            <c:if test="${periodo != null}">
                <input type="hidden" name="idPeriodo" value="${periodo.idPeriodo}">
            </c:if>

            <div class="mb-3">
                <label for="nombrePeriodo" class="form-label">Nombre del Periodo</label>
                <input type="text" class="form-control" id="nombrePeriodo" name="nombrePeriodo" value="${periodo.nombrePeriodo}" placeholder="Ej: 2025-I" required>
            </div>

            <div class="mb-3">
                <label for="fechaInicio" class="form-label">Fecha de Inicio</label>
                <input type="date" class="form-control" id="fechaInicio" name="fechaInicio" value="<fmt:formatDate value='${periodo.fechaInicio}' pattern='yyyy-MM-dd'/>" required>
            </div>

            <div class="mb-3">
                <label for="fechaFin" class="form-label">Fecha de Fin</label>
                <input type="date" class="form-control" id="fechaFin" name="fechaFin" value="<fmt:formatDate value='${periodo.fechaFin}' pattern='yyyy-MM-dd'/>" required>
            </div>

            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="PeriodoAcademicoController?accion=listar" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
