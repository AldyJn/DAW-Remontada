<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>InventoryPro - Sistema de Gestion Empresarial</title>
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

        <h1>InventoryPro</h1>

        <div class="welcome-section">
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 20px; margin-top: 40px;">
                <div style="padding: 40px; background-color: #FFFFFF; border: 1px solid #e8eaed; border-radius: 8px; text-align: center;">
                    <h4 style="color: #5f6368; margin-bottom: 20px; font-weight: 500;">Categorias</h4>
                    <a href="categorias" class="btn btn-primary">Acceder</a>
                </div>

                <div style="padding: 40px; background-color: #FFFFFF; border: 1px solid #e8eaed; border-radius: 8px; text-align: center;">
                    <h4 style="color: #5f6368; margin-bottom: 20px; font-weight: 500;">Inventario</h4>
                    <a href="productos" class="btn btn-primary">Acceder</a>
                </div>
            </div>

        </div>
    </div>
</body>
</html>
