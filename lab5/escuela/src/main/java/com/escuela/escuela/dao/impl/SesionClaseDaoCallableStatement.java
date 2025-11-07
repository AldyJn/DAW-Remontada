package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.SesionClaseDao;
import com.escuela.escuela.entity.SesionClase;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SesionClaseDaoCallableStatement implements SesionClaseDao {

    @Override
    public void insertar(SesionClase sesion) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO sesion_clase (id_curso, id_periodo, fecha, tema) VALUES (?,?,?,?)");
            ps.setString(1, sesion.getIdCurso());
            ps.setInt(2, sesion.getIdPeriodo());
            ps.setDate(3, new java.sql.Date(sesion.getFecha().getTime()));
            ps.setString(4, sesion.getTema());
            ps.executeUpdate();
        } finally {
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(SesionClase sesion) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE sesion_clase SET fecha=?, tema=? WHERE id_sesion=?");
            ps.setDate(1, new java.sql.Date(sesion.getFecha().getTime()));
            ps.setString(2, sesion.getTema());
            ps.setInt(3, sesion.getIdSesion());
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
            ps = conn.prepareStatement("DELETE FROM sesion_clase WHERE id_sesion=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public SesionClase buscar(Integer id) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        SesionClase sesion = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM sesion_clase WHERE id_sesion=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                sesion = new SesionClase();
                sesion.setIdSesion(rs.getInt("id_sesion"));
                sesion.setIdCurso(rs.getString("id_curso"));
                sesion.setIdPeriodo(rs.getInt("id_periodo"));
                sesion.setFecha(rs.getDate("fecha"));
                sesion.setTema(rs.getString("tema"));
            }
            return sesion;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public List<SesionClase> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SesionClase> lista = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM sesion_clase ORDER BY fecha DESC");
            rs = ps.executeQuery();
            while(rs.next()) {
                SesionClase s = new SesionClase();
                s.setIdSesion(rs.getInt("id_sesion"));
                s.setIdCurso(rs.getString("id_curso"));
                s.setIdPeriodo(rs.getInt("id_periodo"));
                s.setFecha(rs.getDate("fecha"));
                s.setTema(rs.getString("tema"));
                lista.add(s);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }
}
