<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${not empty producto}">Editar Producto</c:when>
            <c:otherwise>Nuevo Producto</c:otherwise>
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
                <c:when test="${not empty producto}">Editar Producto</c:when>
                <c:otherwise>Nuevo Producto</c:otherwise>
            </c:choose>
        </h1>

        <c:if test="${empty listaCategorias}">
            <div class="mensaje mensaje-error">
                No hay categorias disponibles. Debe crear al menos una categoria antes de agregar productos.
            </div>
            <div class="mt-20">
                <a href="categorias" class="btn btn-primary">Ir a Categorias</a>
            </div>
        </c:if>

        <c:if test="${not empty listaCategorias}">
            <c:url var="formAction" value="productos">
                <c:param name="action">
                    <c:choose>
                        <c:when test="${not empty producto}">update</c:when>
                        <c:otherwise>insert</c:otherwise>
                    </c:choose>
                </c:param>
            </c:url>

            <form action="${formAction}" method="post">
                <c:if test="${not empty producto}">
                    <input type="hidden" name="id" value="${producto.idProducto}">
                </c:if>

                <div class="form-group">
                    <label for="nombreProducto">Nombre del Producto:</label>
                    <input type="text"
                           id="nombreProducto"
                           name="nombreProducto"
                           value="${producto.nombreProducto}"
                           required
                           maxlength="100"
                           placeholder="Ingrese el nombre del producto">
                </div>

                <div class="form-group">
                    <label for="descripcion">Descripcion:</label>
                    <textarea id="descripcion"
                              name="descripcion"
                              maxlength="255"
                              placeholder="Ingrese una descripcion opcional">${producto.descripcion}</textarea>
                </div>

                <div class="form-group">
                    <label for="precio">Precio:</label>
                    <input type="number"
                           id="precio"
                           name="precio"
                           value="${producto.precio}"
                           required
                           min="0"
                           step="0.01"
                           placeholder="0.00">
                </div>

                <div class="form-group">
                    <label for="stock">Stock:</label>
                    <input type="number"
                           id="stock"
                           name="stock"
                           value="${producto.stock}"
                           required
                           min="0"
                           placeholder="0">
                </div>

                <div class="form-group">
                    <label for="idCategoria">Categoria:</label>
                    <select id="idCategoria" name="idCategoria" required>
                        <option value="">Seleccione una categoria</option>
                        <c:forEach var="categoria" items="${listaCategorias}">
                            <option value="${categoria.idCategoria}"
                                <c:if test="${categoria.idCategoria == producto.idCategoria}">selected</c:if>>
                                ${categoria.nombreCategoria}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mt-20">
                    <button type="submit" class="btn btn-success">
                        <c:choose>
                            <c:when test="${not empty producto}">Actualizar</c:when>
                            <c:otherwise>Guardar</c:otherwise>
                        </c:choose>
                    </button>
                    <a href="productos" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>

            <c:if test="${not empty producto}">
                <div class="info-text mt-20">
                    ID del producto: ${producto.idProducto}
                </div>
            </c:if>
        </c:if>
    </div>
</body>
</html>
