package com.escuela.escuela.service.impl;

import com.escuela.escuela.dao.AsistenciaDao;
import com.escuela.escuela.dao.impl.DaoFactory;
import com.escuela.escuela.entity.Asistencia;
import com.escuela.escuela.service.AsistenciaService;
import java.util.List;
import java.util.Arrays;

public class AsistenciaServiceImpl implements AsistenciaService {
    private AsistenciaDao dao;
    private static final List<String> ESTADOS_VALIDOS = Arrays.asList("asistió", "tardanza", "falta", "justificada");

    public AsistenciaServiceImpl() {
        this.dao = DaoFactory.getAsistenciaDao();
    }

    @Override
    public void registrarAsistencia(Asistencia asistencia) throws Exception {
        if(asistencia.getIdSesion() <= 0) {
            throw new Exception("La sesión de clase es obligatoria");
        }
        if(asistencia.getIdDetalle() <= 0) {
            throw new Exception("El detalle de matrícula es obligatorio");
        }
        if(asistencia.getEstado() == null || !ESTADOS_VALIDOS.contains(asistencia.getEstado())) {
            throw new Exception("El estado debe ser: asistió, tardanza, falta o justificada");
        }
        dao.insertar(asistencia);
    }

    @Override
    public void actualizarAsistencia(Asistencia asistencia) throws Exception {
        if(asistencia.getEstado() == null || !ESTADOS_VALIDOS.contains(asistencia.getEstado())) {
            throw new Exception("El estado debe ser: asistió, tardanza, falta o justificada");
        }
        dao.actualizar(asistencia);
    }

    @Override
    public Asistencia buscarAsistencia(int id) throws Exception {
        return dao.buscar(id);
    }

    @Override
    public List<Asistencia> listarAsistenciasAlumno(int idDetalle) throws Exception {
        return dao.listarAsistenciasAlumno(idDetalle);
    }
}
