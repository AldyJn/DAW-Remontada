package com.escuela.escuela.service;

import com.escuela.escuela.entity.PeriodoAcademico;
import java.util.List;

public interface PeriodoAcademicoService {
    void registrarPeriodo(PeriodoAcademico periodo) throws Exception;
    void actualizarPeriodo(PeriodoAcademico periodo) throws Exception;
    void eliminarPeriodo(int id) throws Exception;
    PeriodoAcademico buscarPeriodo(int id) throws Exception;
    List<PeriodoAcademico> listarPeriodos() throws Exception;
}
