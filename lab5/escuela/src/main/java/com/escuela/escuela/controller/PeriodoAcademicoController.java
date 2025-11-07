package com.escuela.escuela.controller;

import com.escuela.escuela.entity.PeriodoAcademico;
import com.escuela.escuela.service.PeriodoAcademicoService;
import com.escuela.escuela.service.impl.PeriodoAcademicoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/PeriodoAcademicoController")
public class PeriodoAcademicoController extends HttpServlet {
    private PeriodoAcademicoService service = new PeriodoAcademicoServiceImpl();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if(accion == null || accion.equals("listar")) {
                listar(request, response);
            } else if(accion.equals("editar")) {
                editar(request, response);
            } else if(accion.equals("eliminar")) {
                eliminar(request, response);
            } else if(accion.equals("nuevo")) {
                request.getRequestDispatcher("periodoAcademico/form.jsp").forward(request, response);
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
            } else if(accion.equals("actualizar")) {
                actualizar(request, response);
            }
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("periodoAcademico/form.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<PeriodoAcademico> lista = service.listarPeriodos();
        request.setAttribute("periodos", lista);
        request.getRequestDispatcher("periodoAcademico/listar.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        PeriodoAcademico periodo = service.buscarPeriodo(id);
        request.setAttribute("periodo", periodo);
        request.getRequestDispatcher("periodoAcademico/form.jsp").forward(request, response);
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PeriodoAcademico periodo = new PeriodoAcademico();
        periodo.setNombrePeriodo(request.getParameter("nombrePeriodo"));
        periodo.setFechaInicio(sdf.parse(request.getParameter("fechaInicio")));
        periodo.setFechaFin(sdf.parse(request.getParameter("fechaFin")));
        periodo.setEstado(1);

        service.registrarPeriodo(periodo);
        response.sendRedirect("PeriodoAcademicoController?accion=listar");
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PeriodoAcademico periodo = new PeriodoAcademico();
        periodo.setIdPeriodo(Integer.parseInt(request.getParameter("idPeriodo")));
        periodo.setNombrePeriodo(request.getParameter("nombrePeriodo"));
        periodo.setFechaInicio(sdf.parse(request.getParameter("fechaInicio")));
        periodo.setFechaFin(sdf.parse(request.getParameter("fechaFin")));

        service.actualizarPeriodo(periodo);
        response.sendRedirect("PeriodoAcademicoController?accion=listar");
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        service.eliminarPeriodo(id);
        response.sendRedirect("PeriodoAcademicoController?accion=listar");
    }
}
