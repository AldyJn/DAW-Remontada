package com.escuela.escuela.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Stack;

public class DBConn {
    private static DBConn instance;
    private String driver;
    private String url;
    private String username;
    private String password;
    private Stack<Connection> pool;
    private int poolSize;

    private DBConn() {
        loadProperties();
        pool = new Stack<>();
        initializePool();
    }

    private void loadProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar config.properties");
            }
            props.load(input);
            driver = props.getProperty("db.driver");
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            poolSize = Integer.parseInt(props.getProperty("db.pool.size", "10"));

            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar configuración de base de datos", e);
        }
    }

    private void initializePool() {
        try {
            for (int i = 0; i < poolSize; i++) {
                pool.push(createConnection());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar pool de conexiones", e);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static synchronized DBConn getInstance() {
        if (instance == null) {
            instance = new DBConn();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (pool.isEmpty()) {
            return createConnection();
        }
        Connection conn = pool.pop();
        if (conn.isClosed()) {
            return createConnection();
        }
        return conn;
    }

    public synchronized void releaseConnection(Connection conn) {
        if (conn != null && pool.size() < poolSize) {
            try {
                if (!conn.isClosed()) {
                    pool.push(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
