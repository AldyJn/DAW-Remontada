package com.escuela.escuela.controller;

import com.escuela.escuela.entity.Matricula;
import com.escuela.escuela.service.MatriculaService;
import com.escuela.escuela.service.AlumnoService;
import com.escuela.escuela.service.PeriodoAcademicoService;
import com.escuela.escuela.service.impl.MatriculaServiceImpl;
import com.escuela.escuela.service.impl.AlumnoServiceImpl;
import com.escuela.escuela.service.impl.PeriodoAcademicoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/MatriculaController")
public class MatriculaController extends HttpServlet {
    private MatriculaService matriculaService = new MatriculaServiceImpl();
    private AlumnoService alumnoService = new AlumnoServiceImpl();
    private PeriodoAcademicoService periodoService = new PeriodoAcademicoServiceImpl();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if(accion == null || accion.equals("listar")) {
                listar(request, response);
            } else if(accion.equals("nuevo")) {
                nuevo(request, response);
            } else if(accion.equals("retirar")) {
                retirar(request, response);
            } else {
                listar(request, response);
            }
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if(accion.equals("guardar")) {
                guardar(request, response);
            }
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("matricula/form.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Matricula> lista = matriculaService.listarMatriculas();
        request.setAttribute("matriculas", lista);
        request.getRequestDispatcher("matricula/listar.jsp").forward(request, response);
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        request.setAttribute("alumnos", alumnoService.listarAlumnos());
        request.setAttribute("periodos", periodoService.listarPeriodos());
        request.getRequestDispatcher("matricula/form.jsp").forward(request, response);
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Matricula matricula = new Matricula();
        matricula.setIdAlumno(request.getParameter("idAlumno"));
        matricula.setIdPeriodo(Integer.parseInt(request.getParameter("idPeriodo")));
        matricula.setFechaMatricula(sdf.parse(request.getParameter("fechaMatricula")));
        matricula.setEstado(1);

        matriculaService.registrarMatricula(matricula);
        response.sendRedirect("MatriculaController?accion=listar");
    }

    private void retirar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        matriculaService.retirarMatricula(id);
        response.sendRedirect("MatriculaController?accion=listar");
    }
}
