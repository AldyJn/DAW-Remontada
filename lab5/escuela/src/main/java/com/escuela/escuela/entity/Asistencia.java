package com.escuela.escuela.entity;

public class Asistencia {
    private int idAsistencia;
    private int idSesion;
    private int idDetalle;
    private String estado;

    public Asistencia() {}

    public Asistencia(int idAsistencia, int idSesion, int idDetalle, String estado) {
        this.idAsistencia = idAsistencia;
        this.idSesion = idSesion;
        this.idDetalle = idDetalle;
        this.estado = estado;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
