package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.CursoDao;
import com.escuela.escuela.entity.Curso;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDaoCallableStatement implements CursoDao {

    @Override
    public void insertar(Curso curso) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_insertar_curso(?,?,?)}");
            cs.setString(1, curso.getCodigo());
            cs.setString(2, curso.getNombre());
            cs.setInt(3, curso.getCreditos());
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(Curso curso) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_actualizar_curso(?,?,?)}");
            cs.setString(1, curso.getCodigo());
            cs.setString(2, curso.getNombre());
            cs.setInt(3, curso.getCreditos());
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public void eliminar(String codigo) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_eliminar_curso(?)}");
            cs.setString(1, codigo);
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public Curso buscar(String codigo) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        Curso curso = null;
        try {
            cs = conn.prepareCall("{CALL sp_buscar_curso(?)}");
            cs.setString(1, codigo);
            rs = cs.executeQuery();
            if(rs.next()) {
                curso = new Curso();
                curso.setCodigo(rs.getString("codigo"));
                curso.setNombre(rs.getString("nombre"));
                curso.setCreditos(rs.getInt("creditos"));
                curso.setEstado(rs.getInt("estado"));
            }
            return curso;
        } finally {
            Util.close(rs);
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public List<Curso> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Curso> lista = new ArrayList<>();
        try {
            cs = conn.prepareCall("{CALL sp_listar_cursos()}");
            rs = cs.executeQuery();
            while(rs.next()) {
                Curso c = new Curso();
                c.setCodigo(rs.getString("codigo"));
                c.setNombre(rs.getString("nombre"));
                c.setCreditos(rs.getInt("creditos"));
                c.setEstado(rs.getInt("estado"));
                lista.add(c);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public List<Curso> buscarPorNombre(String nombre) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Curso> lista = new ArrayList<>();
        try {
            cs = conn.prepareCall("{CALL sp_buscar_cursos_por_nombre(?)}");
            cs.setString(1, nombre);
            rs = cs.executeQuery();
            while(rs.next()) {
                Curso c = new Curso();
                c.setCodigo(rs.getString("codigo"));
                c.setNombre(rs.getString("nombre"));
                c.setCreditos(rs.getInt("creditos"));
                c.setEstado(rs.getInt("estado"));
                lista.add(c);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public List<Curso> listarPorCreditos(int creditos) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Curso> lista = new ArrayList<>();
        try {
            cs = conn.prepareCall("{CALL sp_listar_cursos_por_creditos(?)}");
            cs.setInt(1, creditos);
            rs = cs.executeQuery();
            while(rs.next()) {
                Curso c = new Curso();
                c.setCodigo(rs.getString("codigo"));
                c.setNombre(rs.getString("nombre"));
                c.setCreditos(rs.getInt("creditos"));
                c.setEstado(rs.getInt("estado"));
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
