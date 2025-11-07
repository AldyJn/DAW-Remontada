package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.EvaluacionDao;
import com.escuela.escuela.entity.Evaluacion;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluacionDaoCallableStatement implements EvaluacionDao {

    @Override
    public void insertar(Evaluacion evaluacion) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO evaluacion (id_curso, nombre, peso) VALUES (?,?,?)");
            ps.setString(1, evaluacion.getIdCurso());
            ps.setString(2, evaluacion.getNombre());
            ps.setDouble(3, evaluacion.getPeso());
            ps.executeUpdate();
        } finally {
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(Evaluacion evaluacion) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE evaluacion SET nombre=?, peso=? WHERE id_evaluacion=?");
            ps.setString(1, evaluacion.getNombre());
            ps.setDouble(2, evaluacion.getPeso());
            ps.setInt(3, evaluacion.getIdEvaluacion());
            ps.executeUpdate();
        } finally {
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM evaluacion WHERE id_evaluacion=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public Evaluacion buscar(Integer id) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Evaluacion evaluacion = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM evaluacion WHERE id_evaluacion=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                evaluacion = new Evaluacion();
                evaluacion.setIdEvaluacion(rs.getInt("id_evaluacion"));
                evaluacion.setIdCurso(rs.getString("id_curso"));
                evaluacion.setNombre(rs.getString("nombre"));
                evaluacion.setPeso(rs.getDouble("peso"));
            }
            return evaluacion;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public List<Evaluacion> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Evaluacion> lista = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM evaluacion");
            rs = ps.executeQuery();
            while(rs.next()) {
                Evaluacion e = new Evaluacion();
                e.setIdEvaluacion(rs.getInt("id_evaluacion"));
                e.setIdCurso(rs.getString("id_curso"));
                e.setNombre(rs.getString("nombre"));
                e.setPeso(rs.getDouble("peso"));
                lista.add(e);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }
}
