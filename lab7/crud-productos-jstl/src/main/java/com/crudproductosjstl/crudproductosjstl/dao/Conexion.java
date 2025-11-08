package com.crudproductosjstl.crudproductosjstl.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase para gestionar la conexion a la base de datos PostgreSQL
 * Implementa el patron Singleton para asegurar una unica instancia
 */
public class Conexion {

    private static Conexion instancia;
    private Connection conexion;

    private String url;
    private String username;
    private String password;
    private String driver;

    /**
     * Constructor privado para implementar Singleton
     * Carga la configuracion desde el archivo database.properties
     */
    private Conexion() {
        try {
            cargarConfiguracion();
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de PostgreSQL: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de configuracion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga la configuracion de la base de datos desde database.properties
     * @throws IOException Si hay error al leer el archivo
     */
    private void cargarConfiguracion() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("database.properties");

        if (inputStream != null) {
            properties.load(inputStream);
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
            driver = properties.getProperty("db.driver");
            inputStream.close();
        } else {
            // Configuracion por defecto si no se encuentra el archivo
            url = "jdbc:postgresql://localhost:5432/crud_productos_db";
            username = "postgres";
            password = "postgres";
            driver = "org.postgresql.Driver";
            System.out.println("Archivo database.properties no encontrado. Usando configuracion por defecto.");
        }
    }

    /**
     * Obtiene la instancia unica de la clase Conexion
     * @return Instancia de Conexion
     */
    public static Conexion getInstancia() {
        if (instancia == null) {
            synchronized (Conexion.class) {
                if (instancia == null) {
                    instancia = new Conexion();
                }
            }
        }
        return instancia;
    }

    /**
     * Obtiene una conexion a la base de datos
     * @return Objeto Connection
     * @throws SQLException Si hay error al conectar
     */
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                conexion = DriverManager.getConnection(url, username, password);
                System.out.println("Conexion a PostgreSQL establecida exitosamente");
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return conexion;
    }

    /**
     * Cierra la conexion a la base de datos
     */
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexion cerrada exitosamente");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Verifica si la conexion esta activa
     * @return true si la conexion esta activa, false en caso contrario
     */
    public boolean isConexionActiva() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
