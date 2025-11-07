package com.escuela.escuela.dao;

import com.escuela.escuela.entity.DetalleMatricula;

public interface DetalleMatriculaDao extends EntidadDao<DetalleMatricula, Integer> {
    void anularDetalle(int idDetalle) throws Exception;
}
