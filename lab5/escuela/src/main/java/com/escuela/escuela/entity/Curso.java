package com.escuela.escuela.entity;

public class Curso {
    private String codigo;
    private String nombre;
    private int creditos;
    private int estado;

    public Curso() {}

    public Curso(String codigo, String nombre, int creditos, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
