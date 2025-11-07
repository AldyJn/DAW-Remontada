package com.escuela.escuela.service;

import com.escuela.escuela.entity.Asistencia;
import java.util.List;

public interface AsistenciaService {
    void registrarAsistencia(Asistencia asistencia) throws Exception;
    void actualizarAsistencia(Asistencia asistencia) throws Exception;
    Asistencia buscarAsistencia(int id) throws Exception;
    List<Asistencia> listarAsistenciasAlumno(int idDetalle) throws Exception;
}
