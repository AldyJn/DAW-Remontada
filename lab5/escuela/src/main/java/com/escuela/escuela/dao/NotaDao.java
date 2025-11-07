package com.escuela.escuela.dao;

import com.escuela.escuela.entity.Nota;
import java.util.List;

public interface NotaDao extends EntidadDao<Nota, Integer> {
    List<Nota> listarNotasAlumno(int idDetalle) throws Exception;
}
