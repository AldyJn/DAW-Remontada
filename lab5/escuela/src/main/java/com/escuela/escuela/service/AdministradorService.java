package com.escuela.escuela.service;

import com.escuela.escuela.entity.Administrador;
import java.util.List;

public interface AdministradorService {
    void registrarAdministrador(Administrador admin) throws Exception;
    void actualizarAdministrador(Administrador admin) throws Exception;
    void eliminarAdministrador(String codigo) throws Exception;
    Administrador buscarAdministrador(String codigo) throws Exception;
    List<Administrador> listarAdministradores() throws Exception;
}
