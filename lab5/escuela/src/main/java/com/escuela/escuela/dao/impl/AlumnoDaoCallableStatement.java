package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.AlumnoDao;
import com.escuela.escuela.entity.Alumno;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDaoCallableStatement implements AlumnoDao {

    @Override
    public void insertar(Alumno alumno) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_insertar_alumno(?,?,?,?)}");
            cs.setString(1, alumno.getCodigo());
            cs.setString(2, alumno.getNombre());
            cs.setString(3, alumno.getApellido());
            cs.setString(4, alumno.getCorreo());
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(Alumno alumno) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_actualizar_alumno(?,?,?,?)}");
            cs.setString(1, alumno.getCodigo());
            cs.setString(2, alumno.getNombre());
            cs.setString(3, alumno.getApellido());
            cs.setString(4, alumno.getCorreo());
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
            cs = conn.prepareCall("{CALL sp_eliminar_alumno(?)}");
            cs.setString(1, codigo);
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public Alumno buscar(String codigo) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        Alumno alumno = null;
        try {
            cs = conn.prepareCall("{CALL sp_buscar_alumno(?)}");
            cs.setString(1, codigo);
            rs = cs.executeQuery();
            if(rs.next()) {
                alumno = new Alumno();
                alumno.setCodigo(rs.getString("codigo"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setCorreo(rs.getString("correo"));
                alumno.setEstado(rs.getInt("estado"));
            }
            return alumno;
        } finally {
            Util.close(rs);
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public List<Alumno> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Alumno> lista = new ArrayList<>();
        try {
            cs = conn.prepareCall("{CALL sp_listar_alumnos()}");
            rs = cs.executeQuery();
            while(rs.next()) {
                Alumno a = new Alumno();
                a.setCodigo(rs.getString("codigo"));
                a.setNombre(rs.getString("nombre"));
                a.setApellido(rs.getString("apellido"));
                a.setCorreo(rs.getString("correo"));
                a.setEstado(rs.getInt("estado"));
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
