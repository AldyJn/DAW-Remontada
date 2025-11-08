package com.crudproductosjstl.crudproductosjstl.dao;

import com.crudproductosjstl.crudproductosjstl.model.Producto;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Producto
 * Implementa las operaciones CRUD sobre la tabla 'producto'
 */
public class ProductoDAO {

    private Connection conexion;

    /**
     * Constructor que obtiene la conexion a la base de datos
     */
    public ProductoDAO() {
        try {
            this.conexion = Conexion.getInstancia().getConexion();
            if (this.conexion == null) {
                throw new RuntimeException("No se pudo establecer conexion con la base de datos. Verifique que PostgreSQL este en ejecucion y las credenciales sean correctas.");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener conexion en ProductoDAO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error de conexion a base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos los productos de la base de datos con el nombre de su categoria
     * @return Lista de objetos Producto
     */
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre_producto, p.descripcion, p.precio, " +
                     "p.stock, p.id_categoria, c.nombre_categoria " +
                     "FROM producto p " +
                     "INNER JOIN categoria c ON p.id_categoria = c.id_categoria " +
                     "ORDER BY p.nombre_producto";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getBigDecimal("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setNombreCategoria(rs.getString("nombre_categoria"));
                productos.add(producto);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
            e.printStackTrace();
        }

        return productos;
    }

    /**
     * Busca un producto por su ID
     * @param id ID del producto a buscar
     * @return Objeto Producto o null si no existe
     */
    public Producto obtenerPorId(int id) {
        Producto producto = null;
        String sql = "SELECT p.id_producto, p.nombre_producto, p.descripcion, p.precio, " +
                     "p.stock, p.id_categoria, c.nombre_categoria " +
                     "FROM producto p " +
                     "INNER JOIN categoria c ON p.id_categoria = c.id_categoria " +
                     "WHERE p.id_producto = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getBigDecimal("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setNombreCategoria(rs.getString("nombre_categoria"));
            }

            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al obtener producto por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return producto;
    }

    /**
     * Lista productos por categoria
     * @param idCategoria ID de la categoria
     * @return Lista de productos de esa categoria
     */
    public List<Producto> listarPorCategoria(int idCategoria) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre_producto, p.descripcion, p.precio, " +
                     "p.stock, p.id_categoria, c.nombre_categoria " +
                     "FROM producto p " +
                     "INNER JOIN categoria c ON p.id_categoria = c.id_categoria " +
                     "WHERE p.id_categoria = ? " +
                     "ORDER BY p.nombre_producto";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, idCategoria);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getBigDecimal("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setNombreCategoria(rs.getString("nombre_categoria"));
                productos.add(producto);
            }

            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al listar productos por categoria: " + e.getMessage());
            e.printStackTrace();
        }

        return productos;
    }

    /**
     * Inserta un nuevo producto en la base de datos
     * @param producto Objeto Producto a insertar
     * @return true si la insercion fue exitosa, false en caso contrario
     */
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO producto (nombre_producto, descripcion, precio, stock, id_categoria) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombreProducto());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setBigDecimal(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getIdCategoria());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza un producto existente en la base de datos
     * @param producto Objeto Producto con los datos actualizados
     * @return true si la actualizacion fue exitosa, false en caso contrario
     */
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre_producto = ?, descripcion = ?, precio = ?, " +
                     "stock = ?, id_categoria = ? WHERE id_producto = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombreProducto());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setBigDecimal(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getIdCategoria());
            pstmt.setInt(6, producto.getIdProducto());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un producto de la base de datos
     * @param id ID del producto a eliminar
     * @return true si la eliminacion fue exitosa, false en caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cuenta el total de productos en la base de datos
     * @return Numero total de productos
     */
    public int contarProductos() {
        String sql = "SELECT COUNT(*) as total FROM producto";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error al contar productos: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Busca productos por nombre (coincidencia parcial)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de productos que coinciden
     */
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre_producto, p.descripcion, p.precio, " +
                     "p.stock, p.id_categoria, c.nombre_categoria " +
                     "FROM producto p " +
                     "INNER JOIN categoria c ON p.id_categoria = c.id_categoria " +
                     "WHERE LOWER(p.nombre_producto) LIKE LOWER(?) " +
                     "ORDER BY p.nombre_producto";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nombre + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getBigDecimal("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setNombreCategoria(rs.getString("nombre_categoria"));
                productos.add(producto);
            }

            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al buscar productos por nombre: " + e.getMessage());
            e.printStackTrace();
        }

        return productos;
    }
}
