package com.escuela.escuela.service.impl;

import com.escuela.escuela.dao.NotaDao;
import com.escuela.escuela.dao.impl.DaoFactory;
import com.escuela.escuela.entity.Nota;
import com.escuela.escuela.service.NotaService;
import java.util.List;

public class NotaServiceImpl implements NotaService {
    private NotaDao dao;

    public NotaServiceImpl() {
        this.dao = DaoFactory.getNotaDao();
    }

    @Override
    public void registrarNota(Nota nota) throws Exception {
        if(nota.getIdDetalle() <= 0) {
            throw new Exception("El detalle de matrícula es obligatorio");
        }
        if(nota.getIdEvaluacion() <= 0) {
            throw new Exception("La evaluación es obligatoria");
        }
        if(nota.getNota() < 0 || nota.getNota() > 20) {
            throw new Exception("La nota debe estar entre 0 y 20");
        }
        dao.insertar(nota);
    }

    @Override
    public void actualizarNota(Nota nota) throws Exception {
        if(nota.getNota() < 0 || nota.getNota() > 20) {
            throw new Exception("La nota debe estar entre 0 y 20");
        }
        dao.actualizar(nota);
    }

    @Override
    public Nota buscarNota(int id) throws Exception {
        return dao.buscar(id);
    }

    @Override
    public List<Nota> listarNotasAlumno(int idDetalle) throws Exception {
        return dao.listarNotasAlumno(idDetalle);
    }
}
