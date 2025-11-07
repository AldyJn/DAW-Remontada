package com.escuela.escuela.service.impl;

import com.escuela.escuela.dao.MatriculaDao;
import com.escuela.escuela.dao.impl.DaoFactory;
import com.escuela.escuela.entity.Matricula;
import com.escuela.escuela.entity.Curso;
import com.escuela.escuela.service.MatriculaService;
import java.util.List;

public class MatriculaServiceImpl implements MatriculaService {
    private MatriculaDao dao;

    public MatriculaServiceImpl() {
        this.dao = DaoFactory.getMatriculaDao();
    }

    @Override
    public void registrarMatricula(Matricula matricula) throws Exception {
        if(matricula.getIdAlumno() == null || matricula.getIdAlumno().trim().isEmpty()) {
            throw new Exception("El código del alumno es obligatorio");
        }
        if(matricula.getIdPeriodo() <= 0) {
            throw new Exception("El periodo académico es obligatorio");
        }
        if(matricula.getFechaMatricula() == null) {
            throw new Exception("La fecha de matrícula es obligatoria");
        }
        dao.insertar(matricula);
    }

    @Override
    public void retirarMatricula(int idMatricula) throws Exception {
        dao.retirarMatricula(idMatricula);
    }

    @Override
    public Matricula buscarMatricula(int id) throws Exception {
        return dao.buscar(id);
    }

    @Override
    public List<Matricula> listarMatriculas() throws Exception {
        return dao.listar();
    }

    @Override
    public List<Curso> listarCursosAlumno(String idAlumno) throws Exception {
        return dao.listarCursosAlumno(idAlumno);
    }
}
