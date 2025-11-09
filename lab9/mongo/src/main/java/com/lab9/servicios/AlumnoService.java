package com.lab9.servicios;

import com.lab9.modelo.documents.Alumno;
import java.util.List;

public interface AlumnoService {
    public void grabar(Alumno alumno);
    public void eliminar(String id);
    public Alumno buscar(String id);
    public List<Alumno> listar();
}
