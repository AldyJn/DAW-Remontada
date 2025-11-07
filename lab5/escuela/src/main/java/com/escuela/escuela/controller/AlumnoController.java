package com.escuela.escuela.controller;

import com.escuela.escuela.entity.Alumno;
import com.escuela.escuela.service.AlumnoService;
import com.escuela.escuela.service.impl.AlumnoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AlumnoController")
public class AlumnoController extends HttpServlet {
    private AlumnoService service = new AlumnoServiceImpl();

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
                request.getRequestDispatcher("alumno/form.jsp").forward(request, response);
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
            request.getRequestDispatcher("alumno/form.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Alumno> lista = service.listarAlumnos();
        request.setAttribute("alumnos", lista);
        request.getRequestDispatcher("alumno/listar.jsp").forward(request, response);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String codigo = request.getParameter("codigo");
        Alumno alumno = service.buscarAlumno(codigo);
        request.setAttribute("alumno", alumno);
        request.getRequestDispatcher("alumno/form.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String codigo = request.getParameter("codigo");
        Alumno alumno = service.buscarAlumno(codigo);
        request.setAttribute("alumno", alumno);
        request.getRequestDispatcher("alumno/form.jsp").forward(request, response);
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Alumno alumno = new Alumno();
        alumno.setCodigo(request.getParameter("codigo"));
        alumno.setNombre(request.getParameter("nombre"));
        alumno.setApellido(request.getParameter("apellido"));
        alumno.setCorreo(request.getParameter("correo"));
        alumno.setEstado(1);

        service.registrarAlumno(alumno);
        response.sendRedirect("AlumnoController?accion=listar");
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Alumno alumno = new Alumno();
        alumno.setCodigo(request.getParameter("codigo"));
        alumno.setNombre(request.getParameter("nombre"));
        alumno.setApellido(request.getParameter("apellido"));
        alumno.setCorreo(request.getParameter("correo"));

        service.actualizarAlumno(alumno);
        response.sendRedirect("AlumnoController?accion=listar");
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String codigo = request.getParameter("codigo");
        service.eliminarAlumno(codigo);
        response.sendRedirect("AlumnoController?accion=listar");
    }
}
