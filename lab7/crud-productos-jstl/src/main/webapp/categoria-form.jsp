<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${not empty categoria}">Editar Categoria</c:when>
            <c:otherwise>Nueva Categoria</c:otherwise>
        </c:choose>
    </title>
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

        <h1>
            <c:choose>
                <c:when test="${not empty categoria}">Editar Categoria</c:when>
                <c:otherwise>Nueva Categoria</c:otherwise>
            </c:choose>
        </h1>

        <c:url var="formAction" value="categorias">
            <c:param name="action">
                <c:choose>
                    <c:when test="${not empty categoria}">update</c:when>
                    <c:otherwise>insert</c:otherwise>
                </c:choose>
            </c:param>
        </c:url>

        <form action="${formAction}" method="post">
            <c:if test="${not empty categoria}">
                <input type="hidden" name="id" value="${categoria.idCategoria}">
            </c:if>

            <div class="form-group">
                <label for="nombreCategoria">Nombre de la Categoria:</label>
                <input type="text"
                       id="nombreCategoria"
                       name="nombreCategoria"
                       value="${categoria.nombreCategoria}"
                       required
                       maxlength="100"
                       placeholder="Ingrese el nombre de la categoria">
            </div>

            <div class="form-group">
                <label for="descripcion">Descripcion:</label>
                <textarea id="descripcion"
                          name="descripcion"
                          maxlength="255"
                          placeholder="Ingrese una descripcion opcional">${categoria.descripcion}</textarea>
            </div>

            <div class="mt-20">
                <button type="submit" class="btn btn-success">
                    <c:choose>
                        <c:when test="${not empty categoria}">Actualizar</c:when>
                        <c:otherwise>Guardar</c:otherwise>
                    </c:choose>
                </button>
                <a href="categorias" class="btn btn-secondary">Cancelar</a>
            </div>
        </form>

        <c:if test="${not empty categoria}">
            <div class="info-text mt-20">
                ID de la categoria: ${categoria.idCategoria}
            </div>
        </c:if>
    </div>
</body>
</html>
