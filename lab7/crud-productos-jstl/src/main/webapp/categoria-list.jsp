<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Categorias</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <nav class="navigation">
            <ul class="nav-links">
                <li><a href="index.jsp">Dashboard</a></li>
                <li><a href="categorias">Categorias</a></li>
                <li><a href="productos">Inventario</a></li>
            </ul>
        </nav>

        <h1>Gestion de Categorias</h1>

        <c:if test="${not empty sessionScope.mensaje}">
            <div class="mensaje mensaje-${sessionScope.tipoMensaje}">
                ${sessionScope.mensaje}
            </div>
            <c:remove var="mensaje" scope="session"/>
            <c:remove var="tipoMensaje" scope="session"/>
        </c:if>

        <div class="mt-20 mb-20">
            <a href="categorias?action=new" class="btn btn-success">Nueva Categoria</a>
        </div>

        <div class="table-container">
            <c:choose>
                <c:when test="${not empty listaCategorias}">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Descripcion</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="categoria" items="${listaCategorias}">
                                <tr>
                                    <td>${categoria.idCategoria}</td>
                                    <td>${categoria.nombreCategoria}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty categoria.descripcion}">
                                                ${categoria.descripcion}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="info-text">Sin descripcion</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="actions">
                                            <a href="categorias?action=edit&id=${categoria.idCategoria}" class="action-link action-edit">Editar</a>
                                            <a href="categorias?action=delete&id=${categoria.idCategoria}"
                                               class="action-link action-delete"
                                               onclick="return confirm('Esta seguro de eliminar esta categoria?');">Eliminar</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="no-data">
                        No hay categorias registradas en el sistema.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <c:if test="${not empty listaCategorias}">
            <div class="info-text mt-20">
                Total de categorias: ${listaCategorias.size()}
            </div>
        </c:if>
    </div>
</body>
</html>
