package com.escuela.escuela.dao;

import com.escuela.escuela.entity.Matricula;
import com.escuela.escuela.entity.Curso;
import java.util.List;

public interface MatriculaDao extends EntidadDao<Matricula, Integer> {
    void retirarMatricula(int idMatricula) throws Exception;
    List<Curso> listarCursosAlumno(String idAlumno) throws Exception;
}
