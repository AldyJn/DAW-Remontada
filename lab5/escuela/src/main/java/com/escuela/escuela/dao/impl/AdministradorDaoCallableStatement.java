package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.AdministradorDao;
import com.escuela.escuela.entity.Administrador;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDaoCallableStatement implements AdministradorDao {

    @Override
    public void insertar(Administrador admin) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_insertar_administrador(?,?,?,?)}");
            cs.setString(1, admin.getCodigo());
            cs.setString(2, admin.getUsuario());
            cs.setString(3, admin.getClave());
            cs.setString(4, admin.getNombre());
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(Administrador admin) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_actualizar_administrador(?,?,?,?)}");
            cs.setString(1, admin.getCodigo());
            cs.setString(2, admin.getUsuario());
            cs.setString(3, admin.getClave());
            cs.setString(4, admin.getNombre());
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
            cs = conn.prepareCall("{CALL sp_eliminar_administrador(?)}");
            cs.setString(1, codigo);
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public Administrador buscar(String codigo) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        Administrador admin = null;
        try {
            cs = conn.prepareCall("{CALL sp_buscar_administrador(?)}");
            cs.setString(1, codigo);
            rs = cs.executeQuery();
            if(rs.next()) {
                admin = new Administrador();
                admin.setCodigo(rs.getString("codigo"));
                admin.setUsuario(rs.getString("usuario"));
                admin.setClave(rs.getString("clave"));
                admin.setNombre(rs.getString("nombre"));
                admin.setEstado(rs.getInt("estado"));
            }
            return admin;
        } finally {
            Util.close(rs);
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public List<Administrador> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Administrador> lista = new ArrayList<>();
        try {
            cs = conn.prepareCall("{CALL sp_listar_administradores()}");
            rs = cs.executeQuery();
            while(rs.next()) {
                Administrador a = new Administrador();
                a.setCodigo(rs.getString("codigo"));
                a.setUsuario(rs.getString("usuario"));
                a.setClave(rs.getString("clave"));
                a.setNombre(rs.getString("nombre"));
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
