package com.escuela.escuela.entity;

public class DetalleMatricula {
    private int idDetalle;
    private int idMatricula;
    private String idCurso;
    private int estado;

    public DetalleMatricula() {}

    public DetalleMatricula(int idDetalle, int idMatricula, String idCurso, int estado) {
        this.idDetalle = idDetalle;
        this.idMatricula = idMatricula;
        this.idCurso = idCurso;
        this.estado = estado;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
