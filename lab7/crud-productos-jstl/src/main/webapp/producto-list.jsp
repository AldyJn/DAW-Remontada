<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Productos</title>
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

        <h1>Gestion de Productos</h1>

        <c:if test="${not empty sessionScope.mensaje}">
            <div class="mensaje mensaje-${sessionScope.tipoMensaje}">
                ${sessionScope.mensaje}
            </div>
            <c:remove var="mensaje" scope="session"/>
            <c:remove var="tipoMensaje" scope="session"/>
        </c:if>

        <div class="mt-20 mb-20">
            <a href="productos?action=new" class="btn btn-success">Nuevo Producto</a>
        </div>

        <div class="table-container">
            <c:choose>
                <c:when test="${not empty listaProductos}">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Descripcion</th>
                                <th>Precio</th>
                                <th>Stock</th>
                                <th>Categoria</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="producto" items="${listaProductos}">
                                <tr>
                                    <td>${producto.idProducto}</td>
                                    <td>${producto.nombreProducto}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty producto.descripcion}">
                                                ${producto.descripcion}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="info-text">Sin descripcion</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="precio">
                                        <fmt:formatNumber value="${producto.precio}" type="currency" currencySymbol="S/ "/>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${producto.stock < 10}">
                                                <span class="stock-bajo">${producto.stock}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="stock-normal">${producto.stock}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${producto.nombreCategoria}</td>
                                    <td>
                                        <div class="actions">
                                            <a href="productos?action=edit&id=${producto.idProducto}" class="action-link action-edit">Editar</a>
                                            <a href="productos?action=delete&id=${producto.idProducto}"
                                               class="action-link action-delete"
                                               onclick="return confirm('Esta seguro de eliminar este producto?');">Eliminar</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="no-data">
                        No hay productos registrados en el sistema.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <c:if test="${not empty listaProductos}">
            <div class="info-text mt-20">
                Total de productos: ${listaProductos.size()}
            </div>
        </c:if>
    </div>
</body>
</html>
