package com.escuela.escuela.controller;

import com.escuela.escuela.entity.Administrador;
import com.escuela.escuela.service.AdministradorService;
import com.escuela.escuela.service.impl.AdministradorServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdministradorController")
public class AdministradorController extends HttpServlet {
    private AdministradorService service = new AdministradorServiceImpl();

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
                request.getRequestDispatcher("administrador/form.jsp").forward(request, response);
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
            request.getRequestDispatcher("administrador/form.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<Administrador> lista = service.listarAdministradores();
        request.setAttribute("administradores", lista);
        request.getRequestDispatcher("administrador/listar.jsp").forward(request, response);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String codigo = request.getParameter("codigo");
        Administrador admin = service.buscarAdministrador(codigo);
        request.setAttribute("administrador", admin);
        request.getRequestDispatcher("administrador/form.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String codigo = request.getParameter("codigo");
        Administrador admin = service.buscarAdministrador(codigo);
        request.setAttribute("administrador", admin);
        request.getRequestDispatcher("administrador/form.jsp").forward(request, response);
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Administrador admin = new Administrador();
        admin.setCodigo(request.getParameter("codigo"));
        admin.setUsuario(request.getParameter("usuario"));
        admin.setClave(request.getParameter("clave"));
        admin.setNombre(request.getParameter("nombre"));
        admin.setEstado(1);

        service.registrarAdministrador(admin);
        response.sendRedirect("AdministradorController?accion=listar");
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Administrador admin = new Administrador();
        admin.setCodigo(request.getParameter("codigo"));
        admin.setUsuario(request.getParameter("usuario"));
        admin.setClave(request.getParameter("clave"));
        admin.setNombre(request.getParameter("nombre"));

        service.actualizarAdministrador(admin);
        response.sendRedirect("AdministradorController?accion=listar");
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String codigo = request.getParameter("codigo");
        service.eliminarAdministrador(codigo);
        response.sendRedirect("AdministradorController?accion=listar");
    }
}
