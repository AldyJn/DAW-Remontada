package com.escuela.escuela.service.impl;

import com.escuela.escuela.dao.CursoDao;
import com.escuela.escuela.dao.impl.DaoFactory;
import com.escuela.escuela.entity.Curso;
import com.escuela.escuela.service.CursoService;
import java.util.List;

public class CursoServiceImpl implements CursoService {
    private CursoDao dao;

    public CursoServiceImpl() {
        this.dao = DaoFactory.getCursoDao();
    }

    @Override
    public void registrarCurso(Curso curso) throws Exception {
        if(curso.getCodigo() == null || curso.getCodigo().trim().isEmpty()) {
            throw new Exception("El código del curso es obligatorio");
        }
        if(curso.getNombre() == null || curso.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del curso es obligatorio");
        }
        if(curso.getCreditos() < 1 || curso.getCreditos() > 6) {
            throw new Exception("Los créditos deben estar entre 1 y 6");
        }
        dao.insertar(curso);
    }

    @Override
    public void actualizarCurso(Curso curso) throws Exception {
        if(curso.getCodigo() == null || curso.getCodigo().trim().isEmpty()) {
            throw new Exception("El código del curso es obligatorio");
        }
        if(curso.getNombre() == null || curso.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del curso es obligatorio");
        }
        if(curso.getCreditos() < 1 || curso.getCreditos() > 6) {
            throw new Exception("Los créditos deben estar entre 1 y 6");
        }
        dao.actualizar(curso);
    }

    @Override
    public void eliminarCurso(String codigo) throws Exception {
        if(codigo == null || codigo.trim().isEmpty()) {
            throw new Exception("El código del curso es obligatorio");
        }
        dao.eliminar(codigo);
    }

    @Override
    public Curso buscarCurso(String codigo) throws Exception {
        return dao.buscar(codigo);
    }

    @Override
    public List<Curso> listarCursos() throws Exception {
        return dao.listar();
    }

    @Override
    public List<Curso> buscarPorNombre(String nombre) throws Exception {
        return dao.buscarPorNombre(nombre);
    }

    @Override
    public List<Curso> listarPorCreditos(int creditos) throws Exception {
        return dao.listarPorCreditos(creditos);
    }
}
