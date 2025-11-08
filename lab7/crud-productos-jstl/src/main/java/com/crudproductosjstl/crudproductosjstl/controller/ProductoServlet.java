package com.crudproductosjstl.crudproductosjstl.controller;

import com.crudproductosjstl.crudproductosjstl.dao.CategoriaDAO;
import com.crudproductosjstl.crudproductosjstl.dao.ProductoDAO;
import com.crudproductosjstl.crudproductosjstl.model.Categoria;
import com.crudproductosjstl.crudproductosjstl.model.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Servlet controlador para gestionar las operaciones CRUD de Producto
 * Implementa el patron MVC como controlador
 */
@WebServlet(name = "ProductoServlet", urlPatterns = {"/productos"})
public class ProductoServlet extends HttpServlet {

    private ProductoDAO productoDAO;
    private CategoriaDAO categoriaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productoDAO = new ProductoDAO();
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
                    eliminarProducto(request, response);
                    break;
                default:
                    listarProductos(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error en ProductoServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("insert".equals(action)) {
                insertarProducto(request, response);
            } else if ("update".equals(action)) {
                actualizarProducto(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error en ProductoServlet POST", e);
        }
    }

    /**
     * Lista todos los productos
     */
    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Producto> listaProductos = productoDAO.listarTodos();
        request.setAttribute("listaProductos", listaProductos);
        request.getRequestDispatcher("producto-list.jsp").forward(request, response);
    }

    /**
     * Muestra el formulario para crear un nuevo producto
     */
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Categoria> listaCategorias = categoriaDAO.listarTodas();
        request.setAttribute("listaCategorias", listaCategorias);
        request.getRequestDispatcher("producto-form.jsp").forward(request, response);
    }

    /**
     * Muestra el formulario para editar un producto existente
     */
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Producto productoExistente = productoDAO.obtenerPorId(id);
        List<Categoria> listaCategorias = categoriaDAO.listarTodas();

        request.setAttribute("producto", productoExistente);
        request.setAttribute("listaCategorias", listaCategorias);
        request.getRequestDispatcher("producto-form.jsp").forward(request, response);
    }

    /**
     * Inserta un nuevo producto en la base de datos
     */
    private void insertarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreProducto = request.getParameter("nombreProducto");
        String descripcion = request.getParameter("descripcion");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        Integer stock = Integer.parseInt(request.getParameter("stock"));
        Integer idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

        Producto nuevoProducto = new Producto(nombreProducto, descripcion, precio, stock, idCategoria);

        boolean resultado = productoDAO.insertar(nuevoProducto);

        if (resultado) {
            request.getSession().setAttribute("mensaje", "Producto creado exitosamente");
            request.getSession().setAttribute("tipoMensaje", "success");
        } else {
            request.getSession().setAttribute("mensaje", "Error al crear el producto");
            request.getSession().setAttribute("tipoMensaje", "error");
        }

        response.sendRedirect("productos");
    }

    /**
     * Actualiza un producto existente
     */
    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nombreProducto = request.getParameter("nombreProducto");
        String descripcion = request.getParameter("descripcion");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        Integer stock = Integer.parseInt(request.getParameter("stock"));
        Integer idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

        Producto producto = new Producto(id, nombreProducto, descripcion, precio, stock, idCategoria);

        boolean resultado = productoDAO.actualizar(producto);

        if (resultado) {
            request.getSession().setAttribute("mensaje", "Producto actualizado exitosamente");
            request.getSession().setAttribute("tipoMensaje", "success");
        } else {
            request.getSession().setAttribute("mensaje", "Error al actualizar el producto");
            request.getSession().setAttribute("tipoMensaje", "error");
        }

        response.sendRedirect("productos");
    }

    /**
     * Elimina un producto
     */
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        boolean resultado = productoDAO.eliminar(id);

        if (resultado) {
            request.getSession().setAttribute("mensaje", "Producto eliminado exitosamente");
            request.getSession().setAttribute("tipoMensaje", "success");
        } else {
            request.getSession().setAttribute("mensaje", "Error al eliminar el producto");
            request.getSession().setAttribute("tipoMensaje", "error");
        }

        response.sendRedirect("productos");
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar operaciones CRUD de Producto";
    }
}
