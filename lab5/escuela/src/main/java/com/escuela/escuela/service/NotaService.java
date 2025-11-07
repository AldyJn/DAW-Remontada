package com.escuela.escuela.service;

import com.escuela.escuela.entity.Nota;
import java.util.List;

public interface NotaService {
    void registrarNota(Nota nota) throws Exception;
    void actualizarNota(Nota nota) throws Exception;
    Nota buscarNota(int id) throws Exception;
    List<Nota> listarNotasAlumno(int idDetalle) throws Exception;
}
