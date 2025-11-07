package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.NotaDao;
import com.escuela.escuela.entity.Nota;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDaoCallableStatement implements NotaDao {

    @Override
    public void insertar(Nota nota) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_registrar_nota(?,?,?)}");
            cs.setInt(1, nota.getIdDetalle());
            cs.setInt(2, nota.getIdEvaluacion());
            cs.setDouble(3, nota.getNota());
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(Nota nota) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE nota SET nota = ? WHERE id_nota = ?");
            ps.setDouble(1, nota.getNota());
            ps.setInt(2, nota.getIdNota());
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
            ps = conn.prepareStatement("DELETE FROM nota WHERE id_nota = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public Nota buscar(Integer id) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Nota nota = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM nota WHERE id_nota = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                nota = new Nota();
                nota.setIdNota(rs.getInt("id_nota"));
                nota.setIdDetalle(rs.getInt("id_detalle"));
                nota.setIdEvaluacion(rs.getInt("id_evaluacion"));
                nota.setNota(rs.getDouble("nota"));
            }
            return nota;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public List<Nota> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Nota> lista = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM nota");
            rs = ps.executeQuery();
            while(rs.next()) {
                Nota n = new Nota();
                n.setIdNota(rs.getInt("id_nota"));
                n.setIdDetalle(rs.getInt("id_detalle"));
                n.setIdEvaluacion(rs.getInt("id_evaluacion"));
                n.setNota(rs.getDouble("nota"));
                lista.add(n);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public List<Nota> listarNotasAlumno(int idDetalle) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Nota> lista = new ArrayList<>();
        try {
            cs = conn.prepareCall("{CALL sp_listar_notas_alumno(?)}");
            cs.setInt(1, idDetalle);
            rs = cs.executeQuery();
            while(rs.next()) {
                Nota n = new Nota();
                n.setIdNota(rs.getInt("id_nota"));
                n.setIdDetalle(rs.getInt("id_detalle"));
                n.setIdEvaluacion(rs.getInt("id_evaluacion"));
                n.setNota(rs.getDouble("nota"));
                lista.add(n);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(cs);
            Util.close(conn);
        }
    }
}
