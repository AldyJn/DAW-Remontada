package com.escuela.escuela.service;

import com.escuela.escuela.entity.Curso;
import java.util.List;

public interface CursoService {
    void registrarCurso(Curso curso) throws Exception;
    void actualizarCurso(Curso curso) throws Exception;
    void eliminarCurso(String codigo) throws Exception;
    Curso buscarCurso(String codigo) throws Exception;
    List<Curso> listarCursos() throws Exception;
    List<Curso> buscarPorNombre(String nombre) throws Exception;
    List<Curso> listarPorCreditos(int creditos) throws Exception;
}
