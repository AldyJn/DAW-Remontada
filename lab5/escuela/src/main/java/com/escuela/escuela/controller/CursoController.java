package com.escuela.escuela.controller;

import com.escuela.escuela.entity.Curso;
import com.escuela.escuela.service.CursoService;
import com.escuela.escuela.service.impl.CursoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/CursoController")
public class CursoController extends HttpServlet {
    private CursoService service = new CursoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if(accion == null || accion.equals("listar")) {
                listar(request, response);
            } else if(accion.equals("buscar")) {
                buscar(request, response);
            } else if(accion.equals("editar")) {
                editar(request, response);
            } else if(accion.equals("eliminar")) {
                eliminar(request, response);
            } else if(accion.equals("nuevo")) {
                request.getRequestDispatcher("curso/form.jsp").forward(request, response);
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
            request.getRequestDispatcher("curso/form.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Curso> lista = service.listarCursos();
        request.setAttribute("cursos", lista);
        request.getRequestDispatcher("curso/listar.jsp").forward(request, response);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String codigo = request.getParameter("codigo");
        Curso curso = service.buscarCurso(codigo);
        request.setAttribute("curso", curso);
        request.getRequestDispatcher("curso/form.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String codigo = request.getParameter("codigo");
        Curso curso = service.buscarCurso(codigo);
        request.setAttribute("curso", curso);
        request.getRequestDispatcher("curso/form.jsp").forward(request, response);
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Curso curso = new Curso();
        curso.setCodigo(request.getParameter("codigo"));
        curso.setNombre(request.getParameter("nombre"));
        curso.setCreditos(Integer.parseInt(request.getParameter("creditos")));
        curso.setEstado(1);

        service.registrarCurso(curso);
        response.sendRedirect("CursoController?accion=listar");
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Curso curso = new Curso();
        curso.setCodigo(request.getParameter("codigo"));
        curso.setNombre(request.getParameter("nombre"));
        curso.setCreditos(Integer.parseInt(request.getParameter("creditos")));

        service.actualizarCurso(curso);
        response.sendRedirect("CursoController?accion=listar");
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String codigo = request.getParameter("codigo");
        service.eliminarCurso(codigo);
        response.sendRedirect("CursoController?accion=listar");
    }
}
