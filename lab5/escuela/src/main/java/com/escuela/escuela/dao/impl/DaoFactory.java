package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.*;
import com.escuela.escuela.util.Tipo;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
    private static Tipo tipoDao;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        Properties props = new Properties();
        try (InputStream input = DaoFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("No se pudo encontrar config.properties");
            }
            props.load(input);
            String tipo = props.getProperty("tipo.dao", "CALLABLE");
            tipoDao = Tipo.valueOf(tipo);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar configuración DAO", e);
        }
    }

    public static AdministradorDao getAdministradorDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new AdministradorDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }

    public static AlumnoDao getAlumnoDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new AlumnoDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }

    public static CursoDao getCursoDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new CursoDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }

    public static MatriculaDao getMatriculaDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new MatriculaDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }

    public static DetalleMatriculaDao getDetalleMatriculaDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new DetalleMatriculaDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }

    public static NotaDao getNotaDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new NotaDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }

    public static AsistenciaDao getAsistenciaDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new AsistenciaDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }

    public static PeriodoAcademicoDao getPeriodoAcademicoDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new PeriodoAcademicoDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }

    public static EvaluacionDao getEvaluacionDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new EvaluacionDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }

    public static SesionClaseDao getSesionClaseDao() {
        switch (tipoDao) {
            case CALLABLE:
                return new SesionClaseDaoCallableStatement();
            case PREPARED:
                throw new UnsupportedOperationException("PreparedStatement no implementado aún");
            default:
                throw new IllegalArgumentException("Tipo de DAO no soportado: " + tipoDao);
        }
    }
}
