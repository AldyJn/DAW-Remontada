package com.escuela.escuela.service.impl;

import com.escuela.escuela.dao.AlumnoDao;
import com.escuela.escuela.dao.impl.DaoFactory;
import com.escuela.escuela.entity.Alumno;
import com.escuela.escuela.service.AlumnoService;
import java.util.List;
import java.util.regex.Pattern;

public class AlumnoServiceImpl implements AlumnoService {
    private AlumnoDao dao;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public AlumnoServiceImpl() {
        this.dao = DaoFactory.getAlumnoDao();
    }

    @Override
    public void registrarAlumno(Alumno alumno) throws Exception {
        if(alumno.getCodigo() == null || alumno.getCodigo().trim().isEmpty()) {
            throw new Exception("El código del alumno es obligatorio");
        }
        if(alumno.getNombre() == null || alumno.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del alumno es obligatorio");
        }
        if(alumno.getApellido() == null || alumno.getApellido().trim().isEmpty()) {
            throw new Exception("El apellido del alumno es obligatorio");
        }
        if(alumno.getCorreo() == null || !EMAIL_PATTERN.matcher(alumno.getCorreo()).matches()) {
            throw new Exception("El correo electrónico no es válido");
        }
        dao.insertar(alumno);
    }

    @Override
    public void actualizarAlumno(Alumno alumno) throws Exception {
        if(alumno.getCodigo() == null || alumno.getCodigo().trim().isEmpty()) {
            throw new Exception("El código del alumno es obligatorio");
        }
        if(alumno.getNombre() == null || alumno.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del alumno es obligatorio");
        }
        if(alumno.getApellido() == null || alumno.getApellido().trim().isEmpty()) {
            throw new Exception("El apellido del alumno es obligatorio");
        }
        if(alumno.getCorreo() == null || !EMAIL_PATTERN.matcher(alumno.getCorreo()).matches()) {
            throw new Exception("El correo electrónico no es válido");
        }
        dao.actualizar(alumno);
    }

    @Override
    public void eliminarAlumno(String codigo) throws Exception {
        if(codigo == null || codigo.trim().isEmpty()) {
            throw new Exception("El código del alumno es obligatorio");
        }
        dao.eliminar(codigo);
    }

    @Override
    public Alumno buscarAlumno(String codigo) throws Exception {
        return dao.buscar(codigo);
    }

    @Override
    public List<Alumno> listarAlumnos() throws Exception {
        return dao.listar();
    }
}
