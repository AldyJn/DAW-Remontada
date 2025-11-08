package com.crudproductosjstl.crudproductosjstl.dao;

import com.crudproductosjstl.crudproductosjstl.model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Categoria
 * Implementa las operaciones CRUD sobre la tabla 'categoria'
 */
public class CategoriaDAO {

    private Connection conexion;

    /**
     * Constructor que obtiene la conexion a la base de datos
     */
    public CategoriaDAO() {
        try {
            this.conexion = Conexion.getInstancia().getConexion();
            if (this.conexion == null) {
                throw new RuntimeException("No se pudo establecer conexion con la base de datos. Verifique que PostgreSQL este en ejecucion y las credenciales sean correctas.");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener conexion en CategoriaDAO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error de conexion a base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todas las categorias de la base de datos
     * @return Lista de objetos Categoria
     */
    public List<Categoria> listarTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre_categoria, descripcion FROM categoria ORDER BY nombre_categoria";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar categorias: " + e.getMessage());
            e.printStackTrace();
        }

        return categorias;
    }

    /**
     * Busca una categoria por su ID
     * @param id ID de la categoria a buscar
     * @return Objeto Categoria o null si no existe
     */
    public Categoria obtenerPorId(int id) {
        Categoria categoria = null;
        String sql = "SELECT id_categoria, nombre_categoria, descripcion FROM categoria WHERE id_categoria = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
            }

            rs.close();

        } catch (SQLException e) {
            System.err.println("Error al obtener categoria por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return categoria;
    }

    /**
     * Inserta una nueva categoria en la base de datos
     * @param categoria Objeto Categoria a insertar
     * @return true si la insercion fue exitosa, false en caso contrario
     */
    public boolean insertar(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre_categoria, descripcion) VALUES (?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombreCategoria());
            pstmt.setString(2, categoria.getDescripcion());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar categoria: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza una categoria existente en la base de datos
     * @param categoria Objeto Categoria con los datos actualizados
     * @return true si la actualizacion fue exitosa, false en caso contrario
     */
    public boolean actualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nombre_categoria = ?, descripcion = ? WHERE id_categoria = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombreCategoria());
            pstmt.setString(2, categoria.getDescripcion());
            pstmt.setInt(3, categoria.getIdCategoria());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar categoria: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una categoria de la base de datos
     * @param id ID de la categoria a eliminar
     * @return true si la eliminacion fue exitosa, false en caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar categoria: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifica si una categoria tiene productos asociados
     * @param id ID de la categoria a verificar
     * @return true si tiene productos asociados, false en caso contrario
     */
    public boolean tieneProductosAsociados(int id) {
        String sql = "SELECT COUNT(*) as total FROM producto WHERE id_categoria = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                rs.close();
                return total > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar productos asociados: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Cuenta el total de categorias en la base de datos
     * @return Numero total de categorias
     */
    public int contarCategorias() {
        String sql = "SELECT COUNT(*) as total FROM categoria";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error al contar categorias: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }
}
