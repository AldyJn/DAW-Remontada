<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Gestión Académica</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #f5f7fa 0%, #e8ecf3 100%);
            min-height: 100vh;
        }

        .navbar {
            background: linear-gradient(135deg, #5e72e4 0%, #825ee4 100%) !important;
            backdrop-filter: blur(10px);
            box-shadow: 0 4px 30px rgba(94, 114, 228, 0.15);
            padding: 1.2rem 0;
            border: none;
        }

        .navbar-brand {
            font-weight: 700;
            font-size: 1.4rem;
            letter-spacing: -0.5px;
        }

        .nav-link {
            font-weight: 500;
            margin: 0 0.3rem;
            transition: all 0.3s ease;
            border-radius: 10px;
            padding: 0.6rem 1.2rem !important;
            font-size: 0.95rem;
        }

        .nav-link:hover, .nav-link.active {
            background: rgba(255, 255, 255, 0.2);
            transform: translateY(-2px);
        }

        .hero {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 80px 0 100px 0;
            margin-bottom: 4rem;
            position: relative;
            overflow: hidden;
        }

        .hero::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background:
                radial-gradient(circle at 20% 50%, rgba(255, 255, 255, 0.1) 0%, transparent 50%),
                radial-gradient(circle at 80% 80%, rgba(255, 255, 255, 0.1) 0%, transparent 50%);
        }

        .hero h1 {
            font-weight: 700;
            font-size: 3.2rem;
            margin-bottom: 1.2rem;
            letter-spacing: -1.5px;
            position: relative;
            z-index: 1;
        }

        .hero p {
            font-size: 1.3rem;
            font-weight: 300;
            opacity: 0.95;
            position: relative;
            z-index: 1;
            max-width: 700px;
            margin: 0 auto;
        }

        .modules-section {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem 4rem;
        }

        .module-card {
            border: none;
            border-radius: 24px;
            transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
            height: 100%;
            background: white;
            overflow: hidden;
            position: relative;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
        }

        .module-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 5px;
            background: linear-gradient(90deg, var(--card-color-1), var(--card-color-2));
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        .module-card:hover::before {
            opacity: 1;
        }

        .module-card:hover {
            transform: translateY(-15px);
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.12);
        }

        .card-body {
            padding: 2.8rem 2rem;
            text-align: center;
        }

        .icon-wrapper {
            width: 90px;
            height: 90px;
            margin: 0 auto 1.8rem;
            border-radius: 22px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, var(--card-color-1), var(--card-color-2));
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.12);
            transition: all 0.3s ease;
        }

        .module-card:hover .icon-wrapper {
            transform: scale(1.1) rotate(5deg);
        }

        .icon-wrapper svg {
            color: white;
            filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.1));
        }

        .card-title {
            font-weight: 600;
            font-size: 1.3rem;
            margin-bottom: 0.8rem;
            color: #2c3e50;
            letter-spacing: -0.5px;
        }

        .card-text {
            color: #7f8c8d;
            font-size: 0.95rem;
            line-height: 1.7;
            margin-bottom: 2rem;
            font-weight: 400;
        }

        .btn-module {
            border: none;
            padding: 0.85rem 2.2rem;
            border-radius: 14px;
            font-weight: 600;
            font-size: 0.95rem;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
            background: linear-gradient(135deg, var(--card-color-1), var(--card-color-2));
            color: white;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.12);
            letter-spacing: 0.3px;
        }

        .btn-module:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            color: white;
        }

        /* Colores educativos vibrantes */
        .card-1 {
            --card-color-1: #667eea;
            --card-color-2: #764ba2;
        }

        .card-2 {
            --card-color-1: #f093fb;
            --card-color-2: #f5576c;
        }

        .card-3 {
            --card-color-1: #4facfe;
            --card-color-2: #00f2fe;
        }

        .card-4 {
            --card-color-1: #43e97b;
            --card-color-2: #38f9d7;
        }

        .card-5 {
            --card-color-1: #fa709a;
            --card-color-2: #fee140;
        }

        .card-6 {
            --card-color-1: #a8edea;
            --card-color-2: #fed6e3;
        }

        .card-7 {
            --card-color-1: #ff9a56;
            --card-color-2: #ff6a88;
        }

        footer {
            background: linear-gradient(135deg, #2c3e50 0%, #3498db 100%);
            margin-top: 3rem;
            padding: 2.5rem 0;
            box-shadow: 0 -4px 30px rgba(52, 152, 219, 0.15);
        }

        footer p {
            margin: 0;
            font-weight: 400;
            font-size: 0.95rem;
            opacity: 0.9;
        }

        @media (max-width: 768px) {
            .hero h1 {
                font-size: 2.2rem;
            }

            .hero p {
                font-size: 1.1rem;
            }

            .modules-section {
                padding: 0 1rem 3rem;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid px-4">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Sistema Académico</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/index.jsp">Inicio</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/AlumnoController">Alumnos</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/CursoController">Cursos</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/AsistenciaController">Asistencias</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/AdministradorController">Administradores</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="hero text-center">
        <div class="container">
            <h1>Sistema de Gestion Academica</h1>
            <p>Administra eficientemente cursos, alumnos, matriculas, notas y asistencias</p>
        </div>
    </div>

    <div class="modules-section">
        <div class="row g-4">
            <div class="col-md-4">
                <div class="card module-card card-1">
                    <div class="card-body">
                        <div class="icon-wrapper">
                            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                            </svg>
                        </div>
                        <h5 class="card-title">Gestion de Alumnos</h5>
                        <p class="card-text">Administra el registro completo de alumnos del sistema academico</p>
                        <a href="${pageContext.request.contextPath}/AlumnoController" class="btn-module">Ir a Alumnos</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card module-card card-2">
                    <div class="card-body">
                        <div class="icon-wrapper">
                            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-book" viewBox="0 0 16 16">
                                <path d="M1 2.828c.885-.37 2.154-.769 3.388-.893 1.33-.134 2.458.063 3.112.752v9.746c-.935-.53-2.12-.603-3.213-.493-1.18.12-2.37.461-3.287.811V2.828zm7.5-.141c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
                            </svg>
                        </div>
                        <h5 class="card-title">Gestion de Cursos</h5>
                        <p class="card-text">Administra el catalogo de cursos disponibles en la institucion</p>
                        <a href="${pageContext.request.contextPath}/CursoController" class="btn-module">Ir a Cursos</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card module-card card-3">
                    <div class="card-body">
                        <div class="icon-wrapper">
                            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">
                                <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>
                                <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319z"/>
                            </svg>
                        </div>
                        <h5 class="card-title">Administradores</h5>
                        <p class="card-text">Gestiona los usuarios administradores del sistema</p>
                        <a href="${pageContext.request.contextPath}/AdministradorController" class="btn-module">Ir a Administradores</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row g-4 mt-3">
            <div class="col-md-4">
                <div class="card module-card card-4">
                    <div class="card-body">
                        <div class="icon-wrapper">
                            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-calendar-range" viewBox="0 0 16 16">
                                <path d="M9 7a1 1 0 0 1 1-1h5v2h-5a1 1 0 0 1-1-1M1 9h4a1 1 0 0 1 0 2H1z"/>
                                <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5M1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4z"/>
                            </svg>
                        </div>
                        <h5 class="card-title">Periodos Academicos</h5>
                        <p class="card-text">Gestiona los periodos academicos del ano escolar</p>
                        <a href="${pageContext.request.contextPath}/PeriodoAcademicoController" class="btn-module">Ir a Periodos</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card module-card card-5">
                    <div class="card-body">
                        <div class="icon-wrapper">
                            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-clipboard-check" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M10.854 7.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7.5 9.793l2.646-2.647a.5.5 0 0 1 .708 0"/>
                                <path d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1h1a1 1 0 0 1 1 1V14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V3.5a1 1 0 0 1 1-1h1z"/>
                                <path d="M9.5 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5zm-3-1A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0z"/>
                            </svg>
                        </div>
                        <h5 class="card-title">Matriculas</h5>
                        <p class="card-text">Registra y gestiona las matriculas de los alumnos</p>
                        <a href="${pageContext.request.contextPath}/MatriculaController" class="btn-module">Ir a Matriculas</a>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card module-card card-6">
                    <div class="card-body">
                        <div class="icon-wrapper">
                            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-bar-chart-fill" viewBox="0 0 16 16">
                                <path d="M1 11a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1zm5-4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1z"/>
                            </svg>
                        </div>
                        <h5 class="card-title">Notas</h5>
                        <p class="card-text">Registro y consulta de calificaciones de los alumnos</p>
                        <a href="${pageContext.request.contextPath}/NotaController" class="btn-module">Ir a Notas</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row g-4 mt-3">
            <div class="col-md-4">
                <div class="card module-card card-7">
                    <div class="card-body">
                        <div class="icon-wrapper">
                            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-calendar-check" viewBox="0 0 16 16">
                                <path d="M10.854 7.146a.5.5 0 0 1 0 .708l-3.5 3.5a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7 10.293l3.146-3.147a.5.5 0 0 1 .708 0"/>
                                <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5M1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4z"/>
                            </svg>
                        </div>
                        <h5 class="card-title">Asistencias</h5>
                        <p class="card-text">Registra y consulta las asistencias de los alumnos</p>
                        <a href="${pageContext.request.contextPath}/AsistenciaController" class="btn-module">Ir a Asistencias</a>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <footer class="text-white text-center">
        <div class="container">
            <p>Sistema de Gestion Academica &copy; 2025 - Desarrollado con Jakarta EE</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>