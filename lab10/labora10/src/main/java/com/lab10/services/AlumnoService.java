package com.lab10.services;

import com.lab10.domain.entities.Alumno;
import java.util.List;

public interface AlumnoService {
    public void grabar(Alumno alumno);
    public void eliminar(int id);
    public Alumno buscar(Integer id);
    public List<Alumno> listar();
}
