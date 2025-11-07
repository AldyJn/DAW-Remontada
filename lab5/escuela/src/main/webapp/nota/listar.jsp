<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notas del Alumno</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>Notas del Alumno</h2>
        <a href="NotaController?accion=registrar" class="btn btn-primary mb-3">Registrar Nota</a>

        <c:if test="${notas != null}">
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>ID Nota</th>
                        <th>ID Detalle</th>
                        <th>ID Evaluacion</th>
                        <th>Nota</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="nota" items="${notas}">
                        <tr>
                            <td>${nota.idNota}</td>
                            <td>${nota.idDetalle}</td>
                            <td>${nota.idEvaluacion}</td>
                            <td><span class="badge ${nota.nota >= 13 ? 'bg-success' : 'bg-danger'}">${nota.nota}</span></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${notas == null}">
            <div class="alert alert-info">
                Por favor, seleccione un detalle de matricula para ver las notas.
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
