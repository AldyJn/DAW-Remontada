<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - InventoryPro</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #5f6368 0%, #80868b 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .error-container {
            background: #FFFFFF;
            max-width: 600px;
            width: 100%;
            padding: 50px 40px;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            text-align: center;
        }

        .error-icon {
            width: 80px;
            height: 80px;
            margin: 0 auto 30px;
            background: #f1f3f4;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 40px;
            color: #5f6368;
        }

        h1 {
            color: #3c4043;
            font-size: 1.8rem;
            margin-bottom: 15px;
            font-weight: 500;
        }

        .error-message {
            color: #5f6368;
            font-size: 1rem;
            line-height: 1.6;
            margin-bottom: 30px;
        }

        .error-actions {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            padding: 12px 28px;
            text-decoration: none;
            border: none;
            border-radius: 6px;
            font-size: 0.95rem;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            display: inline-block;
        }

        .btn-primary {
            background: #5f6368;
            color: #FFFFFF;
        }

        .btn-primary:hover {
            background: #4c5155;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }

        .btn-secondary {
            background: #e8eaed;
            color: #3c4043;
        }

        .btn-secondary:hover {
            background: #d2d5d9;
        }

        .error-details {
            margin-top: 30px;
            padding-top: 30px;
            border-top: 1px solid #e8eaed;
            text-align: left;
        }

        .error-details summary {
            color: #5f6368;
            cursor: pointer;
            font-size: 0.9rem;
            margin-bottom: 10px;
        }

        .error-details pre {
            background: #f1f3f4;
            padding: 15px;
            border-radius: 6px;
            overflow-x: auto;
            font-size: 0.85rem;
            color: #3c4043;
            margin-top: 10px;
        }

        @media (max-width: 600px) {
            .error-container {
                padding: 40px 30px;
            }

            h1 {
                font-size: 1.5rem;
            }

            .error-actions {
                flex-direction: column;
            }

            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">⚠</div>

        <h1>Ha ocurrido un error</h1>

        <p class="error-message">
            Lo sentimos, no pudimos procesar su solicitud en este momento.
            Por favor, intente nuevamente o contacte al administrador del sistema si el problema persiste.
        </p>

        <div class="error-actions">
            <a href="javascript:history.back()" class="btn btn-secondary">Volver</a>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">Ir al Inicio</a>
        </div>

        <% if (request.getAttribute("javax.servlet.error.message") != null) { %>
        <details class="error-details">
            <summary>Detalles técnicos (solo para administradores)</summary>
            <pre><%= request.getAttribute("javax.servlet.error.status_code") != null ?
                "Código: " + request.getAttribute("javax.servlet.error.status_code") + "\n" : "" %><%=
                request.getAttribute("javax.servlet.error.message") != null ?
                request.getAttribute("javax.servlet.error.message") : "Error interno del servidor" %></pre>
        </details>
        <% } %>
    </div>
</body>
</html>
