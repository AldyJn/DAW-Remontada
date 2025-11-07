package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.AsistenciaDao;
import com.escuela.escuela.entity.Asistencia;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDaoCallableStatement implements AsistenciaDao {

    @Override
    public void insertar(Asistencia asistencia) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_registrar_asistencia(?,?,?)}");
            cs.setInt(1, asistencia.getIdSesion());
            cs.setInt(2, asistencia.getIdDetalle());
            cs.setString(3, asistencia.getEstado());
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(Asistencia asistencia) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE asistencia SET estado = ? WHERE id_asistencia = ?");
            ps.setString(1, asistencia.getEstado());
            ps.setInt(2, asistencia.getIdAsistencia());
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
            ps = conn.prepareStatement("DELETE FROM asistencia WHERE id_asistencia = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public Asistencia buscar(Integer id) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Asistencia asistencia = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM asistencia WHERE id_asistencia = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                asistencia = new Asistencia();
                asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
                asistencia.setIdSesion(rs.getInt("id_sesion"));
                asistencia.setIdDetalle(rs.getInt("id_detalle"));
                asistencia.setEstado(rs.getString("estado"));
            }
            return asistencia;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public List<Asistencia> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Asistencia> lista = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM asistencia");
            rs = ps.executeQuery();
            while(rs.next()) {
                Asistencia a = new Asistencia();
                a.setIdAsistencia(rs.getInt("id_asistencia"));
                a.setIdSesion(rs.getInt("id_sesion"));
                a.setIdDetalle(rs.getInt("id_detalle"));
                a.setEstado(rs.getString("estado"));
                lista.add(a);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public List<Asistencia> listarAsistenciasAlumno(int idDetalle) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Asistencia> lista = new ArrayList<>();
        try {
            cs = conn.prepareCall("{CALL sp_listar_asistencias_alumno(?)}");
            cs.setInt(1, idDetalle);
            rs = cs.executeQuery();
            while(rs.next()) {
                Asistencia a = new Asistencia();
                a.setIdAsistencia(rs.getInt("id_asistencia"));
                a.setIdSesion(rs.getInt("id_sesion"));
                a.setIdDetalle(rs.getInt("id_detalle"));
                a.setEstado(rs.getString("estado"));
                lista.add(a);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(cs);
            Util.close(conn);
        }
    }
}
