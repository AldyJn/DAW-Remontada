package com.escuela.escuela.dao;

import com.escuela.escuela.entity.Curso;
import java.util.List;

public interface CursoDao extends EntidadDao<Curso, String> {
    List<Curso> buscarPorNombre(String nombre) throws Exception;
    List<Curso> listarPorCreditos(int creditos) throws Exception;
}
