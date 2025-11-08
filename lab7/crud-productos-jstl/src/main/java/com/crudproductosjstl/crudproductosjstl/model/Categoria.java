package com.crudproductosjstl.crudproductosjstl.model;

import java.io.Serializable;

/**
 * Clase modelo que representa una Categoria
 * Corresponde a la tabla 'categoria' en la base de datos
 */
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idCategoria;
    private String nombreCategoria;
    private String descripcion;

    /**
     * Constructor por defecto
     */
    public Categoria() {
    }

    /**
     * Constructor completo con ID
     * @param idCategoria Identificador unico de la categoria
     * @param nombreCategoria Nombre de la categoria
     * @param descripcion Descripcion de la categoria
     */
    public Categoria(Integer idCategoria, String nombreCategoria, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
    }

    /**
     * Constructor sin ID (para crear nuevas categorias)
     * @param nombreCategoria Nombre de la categoria
     * @param descripcion Descripcion de la categoria
     */
    public Categoria(String nombreCategoria, String descripcion) {
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        return idCategoria != null ? idCategoria.equals(categoria.idCategoria) : categoria.idCategoria == null;
    }

    @Override
    public int hashCode() {
        return idCategoria != null ? idCategoria.hashCode() : 0;
    }
}
