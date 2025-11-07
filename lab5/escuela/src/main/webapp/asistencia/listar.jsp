<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Asistencias del Alumno</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>Control de Asistencia</h2>
        <a href="AsistenciaController?accion=registrar" class="btn btn-primary mb-3">Registrar Asistencia</a>

        <c:if test="${asistencias != null}">
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>ID Sesión</th>
                        <th>ID Detalle</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="asistencia" items="${asistencias}">
                        <tr>
                            <td>${asistencia.idAsistencia}</td>
                            <td>${asistencia.idSesion}</td>
                            <td>${asistencia.idDetalle}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${asistencia.estado == 'asistió'}">
                                        <span class="badge bg-success">Asistió</span>
                                    </c:when>
                                    <c:when test="${asistencia.estado == 'tardanza'}">
                                        <span class="badge bg-warning">Tardanza</span>
                                    </c:when>
                                    <c:when test="${asistencia.estado == 'falta'}">
                                        <span class="badge bg-danger">Falta</span>
                                    </c:when>
                                    <c:when test="${asistencia.estado == 'justificada'}">
                                        <span class="badge bg-info">Justificada</span>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${asistencias == null}">
            <div class="alert alert-info">
                Por favor, seleccione un detalle de matrícula para ver las asistencias.
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
