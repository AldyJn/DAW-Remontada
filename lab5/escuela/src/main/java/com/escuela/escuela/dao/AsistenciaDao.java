package com.escuela.escuela.dao;

import com.escuela.escuela.entity.Asistencia;
import java.util.List;

public interface AsistenciaDao extends EntidadDao<Asistencia, Integer> {
    List<Asistencia> listarAsistenciasAlumno(int idDetalle) throws Exception;
}
