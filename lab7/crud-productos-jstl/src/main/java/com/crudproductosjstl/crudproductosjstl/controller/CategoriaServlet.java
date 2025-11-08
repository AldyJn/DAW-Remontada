package com.crudproductosjstl.crudproductosjstl.controller;

import com.crudproductosjstl.crudproductosjstl.dao.CategoriaDAO;
import com.crudproductosjstl.crudproductosjstl.model.Categoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet controlador para gestionar las operaciones CRUD de Categoria
 * Implementa el patron MVC como controlador
 */
@WebServlet(name = "CategoriaServlet", urlPatterns = {"/categorias"})
public class CategoriaServlet extends HttpServlet {

    private CategoriaDAO categoriaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "edit":
                    mostrarFormularioEditar(request, response);
                    break;
                case "delete":
                    eliminarCategoria(request, response);
                    break;
                default:
                    listarCategorias(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error en CategoriaServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("insert".equals(action)) {
                insertarCategoria(request, response);
            } else if ("update".equals(action)) {
                actualizarCategoria(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error en CategoriaServlet POST", e);
        }
    }

    /**
     * Lista todas las categorias
     */
    private void listarCategorias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Categoria> listaCategorias = categoriaDAO.listarTodas();
        request.setAttribute("listaCategorias", listaCategorias);
        request.getRequestDispatcher("categoria-list.jsp").forward(request, response);
    }

    /**
     * Muestra el formulario para crear una nueva categoria
     */
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("categoria-form.jsp").forward(request, response);
    }

    /**
     * Muestra el formulario para editar una categoria existente
     */
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Categoria categoriaExistente = categoriaDAO.obtenerPorId(id);
        request.setAttribute("categoria", categoriaExistente);
        request.getRequestDispatcher("categoria-form.jsp").forward(request, response);
    }

    /**
     * Inserta una nueva categoria en la base de datos
     */
    private void insertarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreCategoria = request.getParameter("nombreCategoria");
        String descripcion = request.getParameter("descripcion");

        Categoria nuevaCategoria = new Categoria(nombreCategoria, descripcion);

        boolean resultado = categoriaDAO.insertar(nuevaCategoria);

        if (resultado) {
            request.getSession().setAttribute("mensaje", "Categoria creada exitosamente");
            request.getSession().setAttribute("tipoMensaje", "success");
        } else {
            request.getSession().setAttribute("mensaje", "Error al crear la categoria");
            request.getSession().setAttribute("tipoMensaje", "error");
        }

        response.sendRedirect("categorias");
    }

    /**
     * Actualiza una categoria existente
     */
    private void actualizarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nombreCategoria = request.getParameter("nombreCategoria");
        String descripcion = request.getParameter("descripcion");

        Categoria categoria = new Categoria(id, nombreCategoria, descripcion);

        boolean resultado = categoriaDAO.actualizar(categoria);

        if (resultado) {
            request.getSession().setAttribute("mensaje", "Categoria actualizada exitosamente");
            request.getSession().setAttribute("tipoMensaje", "success");
        } else {
            request.getSession().setAttribute("mensaje", "Error al actualizar la categoria");
            request.getSession().setAttribute("tipoMensaje", "error");
        }

        response.sendRedirect("categorias");
    }

    /**
     * Elimina una categoria
     */
    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        boolean tieneProductos = categoriaDAO.tieneProductosAsociados(id);

        if (tieneProductos) {
            request.getSession().setAttribute("mensaje",
                "No se puede eliminar la categoria porque tiene productos asociados");
            request.getSession().setAttribute("tipoMensaje", "error");
        } else {
            boolean resultado = categoriaDAO.eliminar(id);

            if (resultado) {
                request.getSession().setAttribute("mensaje", "Categoria eliminada exitosamente");
                request.getSession().setAttribute("tipoMensaje", "success");
            } else {
                request.getSession().setAttribute("mensaje", "Error al eliminar la categoria");
                request.getSession().setAttribute("tipoMensaje", "error");
            }
        }

        response.sendRedirect("categorias");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar operaciones CRUD de Categoria";
    }
}
