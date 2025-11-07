package com.escuela.escuela.service;

import com.escuela.escuela.entity.Alumno;
import java.util.List;

public interface AlumnoService {
    void registrarAlumno(Alumno alumno) throws Exception;
    void actualizarAlumno(Alumno alumno) throws Exception;
    void eliminarAlumno(String codigo) throws Exception;
    Alumno buscarAlumno(String codigo) throws Exception;
    List<Alumno> listarAlumnos() throws Exception;
}
