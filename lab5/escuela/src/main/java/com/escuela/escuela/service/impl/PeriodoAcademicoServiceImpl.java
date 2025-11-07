package com.escuela.escuela.service.impl;

import com.escuela.escuela.dao.PeriodoAcademicoDao;
import com.escuela.escuela.dao.impl.DaoFactory;
import com.escuela.escuela.entity.PeriodoAcademico;
import com.escuela.escuela.service.PeriodoAcademicoService;
import java.util.List;

public class PeriodoAcademicoServiceImpl implements PeriodoAcademicoService {
    private PeriodoAcademicoDao dao;

    public PeriodoAcademicoServiceImpl() {
        this.dao = DaoFactory.getPeriodoAcademicoDao();
    }

    @Override
    public void registrarPeriodo(PeriodoAcademico periodo) throws Exception {
        if(periodo.getNombrePeriodo() == null || periodo.getNombrePeriodo().trim().isEmpty()) {
            throw new Exception("El nombre del periodo es obligatorio");
        }
        if(periodo.getFechaInicio() == null) {
            throw new Exception("La fecha de inicio es obligatoria");
        }
        if(periodo.getFechaFin() == null) {
            throw new Exception("La fecha de fin es obligatoria");
        }
        if(periodo.getFechaFin().before(periodo.getFechaInicio())) {
            throw new Exception("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        dao.insertar(periodo);
    }

    @Override
    public void actualizarPeriodo(PeriodoAcademico periodo) throws Exception {
        if(periodo.getNombrePeriodo() == null || periodo.getNombrePeriodo().trim().isEmpty()) {
            throw new Exception("El nombre del periodo es obligatorio");
        }
        if(periodo.getFechaInicio() == null) {
            throw new Exception("La fecha de inicio es obligatoria");
        }
        if(periodo.getFechaFin() == null) {
            throw new Exception("La fecha de fin es obligatoria");
        }
        if(periodo.getFechaFin().before(periodo.getFechaInicio())) {
            throw new Exception("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        dao.actualizar(periodo);
    }

    @Override
    public void eliminarPeriodo(int id) throws Exception {
        dao.eliminar(id);
    }

    @Override
    public PeriodoAcademico buscarPeriodo(int id) throws Exception {
        return dao.buscar(id);
    }

    @Override
    public List<PeriodoAcademico> listarPeriodos() throws Exception {
        return dao.listar();
    }
}
