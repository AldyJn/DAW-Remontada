package com.escuela.escuela.dao;

import java.util.List;

public interface EntidadDao<T, V> {
    void insertar(T entity) throws Exception;
    void actualizar(T entity) throws Exception;
    void eliminar(V id) throws Exception;
    T buscar(V id) throws Exception;
    List<T> listar() throws Exception;
}
