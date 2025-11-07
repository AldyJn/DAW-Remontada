package com.escuela.escuela.dao.impl;

import com.escuela.escuela.dao.DetalleMatriculaDao;
import com.escuela.escuela.entity.DetalleMatricula;
import com.escuela.escuela.util.DBConn;
import com.escuela.escuela.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleMatriculaDaoCallableStatement implements DetalleMatriculaDao {

    @Override
    public void insertar(DetalleMatricula detalle) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_insertar_detalle_matricula(?,?)}");
            cs.setInt(1, detalle.getIdMatricula());
            cs.setString(2, detalle.getIdCurso());
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }

    @Override
    public void actualizar(DetalleMatricula detalle) throws Exception {
        throw new UnsupportedOperationException("No se permite actualizar detalles de matrícula");
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        throw new UnsupportedOperationException("Usar anularDetalle() en su lugar");
    }

    @Override
    public DetalleMatricula buscar(Integer id) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        DetalleMatricula detalle = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM detalle_matricula WHERE id_detalle = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                detalle = new DetalleMatricula();
                detalle.setIdDetalle(rs.getInt("id_detalle"));
                detalle.setIdMatricula(rs.getInt("id_matricula"));
                detalle.setIdCurso(rs.getString("id_curso"));
                detalle.setEstado(rs.getInt("estado"));
            }
            return detalle;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public List<DetalleMatricula> listar() throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DetalleMatricula> lista = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM detalle_matricula WHERE estado = 1");
            rs = ps.executeQuery();
            while(rs.next()) {
                DetalleMatricula d = new DetalleMatricula();
                d.setIdDetalle(rs.getInt("id_detalle"));
                d.setIdMatricula(rs.getInt("id_matricula"));
                d.setIdCurso(rs.getString("id_curso"));
                d.setEstado(rs.getInt("estado"));
                lista.add(d);
            }
            return lista;
        } finally {
            Util.close(rs);
            Util.close(ps);
            Util.close(conn);
        }
    }

    @Override
    public void anularDetalle(int idDetalle) throws Exception {
        Connection conn = DBConn.getInstance().getConnection();
        CallableStatement cs = null;
        try {
            cs = conn.prepareCall("{CALL sp_anular_detalle_matricula(?)}");
            cs.setInt(1, idDetalle);
            cs.execute();
        } finally {
            Util.close(cs);
            Util.close(conn);
        }
    }
}
