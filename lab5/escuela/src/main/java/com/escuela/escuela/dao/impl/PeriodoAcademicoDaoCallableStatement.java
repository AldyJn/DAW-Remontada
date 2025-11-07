package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.PeriodoAcademicoDao;
import com.escuela.escuela.entity.PeriodoAcademico;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeriodoAcademicoDaoCallableStatement implements PeriodoAcademicoDao {

    @Override
    public void insertar(PeriodoAcademico periodo) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO periodo_academico (nombre_periodo, fecha_inicio, fecha_fin, estado) VALUES (?,?,?,1)");
            ps.setString(1, periodo.getNombrePeriodo());
            ps.setDate(2, new java.sql.Date(periodo.getFechaInicio().getTime()));
            ps.setDate(3, new java.sql.Date(periodo.getFechaFin().getTime()));
            ps.executeUpdate();
        } finally {
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(PeriodoAcademico periodo) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE periodo_academico SET nombre_periodo=?, fecha_inicio=?, fecha_fin=? WHERE id_periodo=?");
            ps.setString(1, periodo.getNombrePeriodo());
            ps.setDate(2, new java.sql.Date(periodo.getFechaInicio().getTime()));
            ps.setDate(3, new java.sql.Date(periodo.getFechaFin().getTime()));
            ps.setInt(4, periodo.getIdPeriodo());
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
            ps = conn.prepareStatement("UPDATE periodo_academico SET estado=0 WHERE id_periodo=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public PeriodoAcademico buscar(Integer id) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        PeriodoAcademico periodo = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM periodo_academico WHERE id_periodo=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                periodo = new PeriodoAcademico();
                periodo.setIdPeriodo(rs.getInt("id_periodo"));
                periodo.setNombrePeriodo(rs.getString("nombre_periodo"));
                periodo.setFechaInicio(rs.getDate("fecha_inicio"));
                periodo.setFechaFin(rs.getDate("fecha_fin"));
                periodo.setEstado(rs.getInt("estado"));
            }
            return periodo;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public List<PeriodoAcademico> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PeriodoAcademico> lista = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM periodo_academico WHERE estado=1 ORDER BY fecha_inicio DESC");
            rs = ps.executeQuery();
            while(rs.next()) {
                PeriodoAcademico p = new PeriodoAcademico();
                p.setIdPeriodo(rs.getInt("id_periodo"));
                p.setNombrePeriodo(rs.getString("nombre_periodo"));
                p.setFechaInicio(rs.getDate("fecha_inicio"));
                p.setFechaFin(rs.getDate("fecha_fin"));
                p.setEstado(rs.getInt("estado"));
                lista.add(p);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }
}
