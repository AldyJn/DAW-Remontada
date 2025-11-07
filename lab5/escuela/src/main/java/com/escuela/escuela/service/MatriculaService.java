package com.escuela.escuela.service;

import com.escuela.escuela.entity.Matricula;
import com.escuela.escuela.entity.Curso;
import java.util.List;

public interface MatriculaService {
    void registrarMatricula(Matricula matricula) throws Exception;
    void retirarMatricula(int idMatricula) throws Exception;
    Matricula buscarMatricula(int id) throws Exception;
    List<Matricula> listarMatriculas() throws Exception;
    List<Curso> listarCursosAlumno(String idAlumno) throws Exception;
}
