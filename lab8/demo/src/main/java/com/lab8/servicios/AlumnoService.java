package com.lab8.servicios;

import com.lab8.modelo.entidades.Alumno;
import java.util.List;

public interface AlumnoService {
    public List<Alumno> listar();
    public void grabar(Alumno alumno);
    public Alumno buscar(Integer id);
    public void eliminar(Integer id);
}
