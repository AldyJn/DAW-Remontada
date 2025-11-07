package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.MatriculaDao;
import com.escuela.escuela.entity.Matricula;
import com.escuela.escuela.entity.Curso;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDaoCallableStatement implements MatriculaDao {

    @Override
    public void insertar(Matricula matricula) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_insertar_matricula(?,?,?)}");
            cs.setString(1, matricula.getIdAlumno());
            cs.setInt(2, matricula.getIdPeriodo());
            cs.setDate(3, new java.sql.Date(matricula.getFechaMatricula().getTime()));
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(Matricula matricula) throws Exception {
        throw new UnsupportedOperationException("No se permite actualizar matrículas");
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        throw new UnsupportedOperationException("Usar retirarMatricula() en su lugar");
    }

    @Override
    public Matricula buscar(Integer id) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Matricula matricula = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM matricula WHERE id_matricula = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                matricula = new Matricula();
                matricula.setIdMatricula(rs.getInt("id_matricula"));
                matricula.setIdAlumno(rs.getString("id_alumno"));
                matricula.setIdPeriodo(rs.getInt("id_periodo"));
                matricula.setFechaMatricula(rs.getDate("fecha_matricula"));
                matricula.setEstado(rs.getInt("estado"));
            }
            return matricula;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public List<Matricula> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Matricula> lista = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM matricula WHERE estado = 1");
            rs = ps.executeQuery();
            while(rs.next()) {
                Matricula m = new Matricula();
                m.setIdMatricula(rs.getInt("id_matricula"));
                m.setIdAlumno(rs.getString("id_alumno"));
                m.setIdPeriodo(rs.getInt("id_periodo"));
                m.setFechaMatricula(rs.getDate("fecha_matricula"));
                m.setEstado(rs.getInt("estado"));
                lista.add(m);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public void retirarMatricula(int idMatricula) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_retirar_matricula(?)}");
            cs.setInt(1, idMatricula);
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public List<Curso> listarCursosAlumno(String idAlumno) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Curso> lista = new ArrayList<>();
        try {
            cs = conn.prepareCall("{CALL sp_listar_cursos_alumno(?)}");
            cs.setString(1, idAlumno);
            rs = cs.executeQuery();
            while(rs.next()) {
                Curso c = new Curso();
                c.setCodigo(rs.getString("codigo"));
                c.setNombre(rs.getString("nombre"));
                c.setCreditos(rs.getInt("creditos"));
                lista.add(c);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(cs);
            Util.close(conn);
        }
    }
}
