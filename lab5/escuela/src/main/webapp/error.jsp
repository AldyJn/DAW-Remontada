<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Sistema Académico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #e8ecf3 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .error-container {
            max-width: 600px;
            background: white;
            border-radius: 24px;
            padding: 3rem;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .error-icon {
            width: 100px;
            height: 100px;
            margin: 0 auto 2rem;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 10px 30px rgba(240, 147, 251, 0.3);
        }

        .error-icon svg {
            color: white;
            filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.1));
        }

        h1 {
            color: #2c3e50;
            font-weight: 700;
            font-size: 2rem;
            margin-bottom: 1rem;
        }

        .error-message {
            background: #fef5f5;
            border-left: 4px solid #f5576c;
            padding: 1.5rem;
            border-radius: 12px;
            margin: 2rem 0;
            text-align: left;
        }

        .error-message p {
            margin: 0;
            color: #d63031;
            font-weight: 500;
        }

        .error-details {
            background: #f8f9fa;
            padding: 1rem;
            border-radius: 8px;
            margin-top: 1rem;
            font-size: 0.9rem;
            color: #666;
            text-align: left;
            font-family: monospace;
            max-height: 200px;
            overflow-y: auto;
        }

        .btn-home {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            padding: 0.85rem 2.5rem;
            border-radius: 14px;
            font-weight: 600;
            color: white;
            text-decoration: none;
            display: inline-block;
            margin-top: 1.5rem;
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.3);
            transition: all 0.3s ease;
        }

        .btn-home:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 30px rgba(102, 126, 234, 0.4);
            color: white;
        }

        .btn-back {
            background: #e9ecef;
            border: none;
            padding: 0.85rem 2.5rem;
            border-radius: 14px;
            font-weight: 600;
            color: #495057;
            text-decoration: none;
            display: inline-block;
            margin-top: 1.5rem;
            margin-left: 1rem;
            transition: all 0.3s ease;
        }

        .btn-back:hover {
            background: #dee2e6;
            transform: translateY(-2px);
            color: #495057;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-exclamation-triangle" viewBox="0 0 16 16">
                <path d="M7.938 2.016A.13.13 0 0 1 8.002 2a.13.13 0 0 1 .063.016.146.146 0 0 1 .054.057l6.857 11.667c.036.06.035.124.002.183a.163.163 0 0 1-.054.06.116.116 0 0 1-.066.017H1.146a.115.115 0 0 1-.066-.017.163.163 0 0 1-.054-.06.176.176 0 0 1 .002-.183L7.884 2.073a.147.147 0 0 1 .054-.057zm1.044-.45a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566z"/>
                <path d="M7.002 12a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 5.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995z"/>
            </svg>
        </div>

        <h1>Ha ocurrido un error</h1>
        <p style="color: #7f8c8d; margin-bottom: 2rem;">
            Lo sentimos, algo salió mal al procesar tu solicitud.
        </p>

        <%
        String errorMsg = (String) request.getAttribute("error");
        Exception customException = (Exception) request.getAttribute("exception");
        Throwable currentException = (customException != null) ? customException : exception;

        if (errorMsg != null && !errorMsg.isEmpty()) {
        %>
            <div class="error-message">
                <p><strong>Error:</strong> <%= errorMsg %></p>
            </div>
        <% } %>

        <% if (currentException != null) { %>
            <div class="error-details">
                <strong>Detalles técnicos:</strong><br>
                <%= currentException.getClass().getName() %>: <%= currentException.getMessage() %>
            </div>
        <% } %>

        <% if (errorMsg == null && currentException == null) { %>
            <div class="error-message">
                <p>No se pudo completar la operación solicitada. Por favor, intenta nuevamente.</p>
            </div>
        <% } %>

        <div style="margin-top: 2rem;">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-home">Volver al Inicio</a>
            <a href="javascript:history.back()" class="btn-back">Volver Atrás</a>
        </div>

        <p style="color: #95a5a6; margin-top: 2rem; font-size: 0.9rem;">
            Si el problema persiste, contacta al administrador del sistema.
        </p>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
