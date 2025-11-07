package com.escuela.escuela.entity;

public class Administrador {
    private String codigo;
    private String usuario;
    private String clave;
    private String nombre;
    private int estado;

    public Administrador() {}

    public Administrador(String codigo, String usuario, String clave, String nombre, int estado) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.clave = clave;
        this.nombre = nombre;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
