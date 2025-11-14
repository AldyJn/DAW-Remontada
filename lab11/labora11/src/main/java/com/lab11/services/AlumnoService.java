package com.lab11.services;

import com.lab11.domain.entities.Alumno;
import java.util.List;

public interface AlumnoService {
    public List<Alumno> listar();
    public void grabar(Alumno alumno);
    public Alumno buscar(Integer id);
    public void eliminar(Integer id);
}
