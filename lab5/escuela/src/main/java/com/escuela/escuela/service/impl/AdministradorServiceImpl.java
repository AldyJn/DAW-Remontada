package com.escuela.escuela.service.impl;

import com.escuela.escuela.dao.AdministradorDao;
import com.escuela.escuela.dao.impl.DaoFactory;
import com.escuela.escuela.entity.Administrador;
import com.escuela.escuela.service.AdministradorService;
import java.util.List;

public class AdministradorServiceImpl implements AdministradorService {
    private AdministradorDao dao;

    public AdministradorServiceImpl() {
        this.dao = DaoFactory.getAdministradorDao();
    }

    @Override
    public void registrarAdministrador(Administrador admin) throws Exception {
        if(admin.getCodigo() == null || admin.getCodigo().trim().isEmpty()) {
            throw new Exception("El código del administrador es obligatorio");
        }
        if(admin.getUsuario() == null || admin.getUsuario().trim().isEmpty()) {
            throw new Exception("El usuario es obligatorio");
        }
        if(admin.getClave() == null || admin.getClave().length() < 6) {
            throw new Exception("La clave debe tener al menos 6 caracteres");
        }
        if(admin.getNombre() == null || admin.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del administrador es obligatorio");
        }
        dao.insertar(admin);
    }

    @Override
    public void actualizarAdministrador(Administrador admin) throws Exception {
        if(admin.getCodigo() == null || admin.getCodigo().trim().isEmpty()) {
            throw new Exception("El código del administrador es obligatorio");
        }
        if(admin.getUsuario() == null || admin.getUsuario().trim().isEmpty()) {
            throw new Exception("El usuario es obligatorio");
        }
        if(admin.getClave() == null || admin.getClave().length() < 6) {
            throw new Exception("La clave debe tener al menos 6 caracteres");
        }
        if(admin.getNombre() == null || admin.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del administrador es obligatorio");
        }
        dao.actualizar(admin);
    }

    @Override
    public void eliminarAdministrador(String codigo) throws Exception {
        if(codigo == null || codigo.trim().isEmpty()) {
            throw new Exception("El código del administrador es obligatorio");
        }
        dao.eliminar(codigo);
    }

    @Override
    public Administrador buscarAdministrador(String codigo) throws Exception {
        return dao.buscar(codigo);
    }

    @Override
    public List<Administrador> listarAdministradores() throws Exception {
        return dao.listar();
    }
}
