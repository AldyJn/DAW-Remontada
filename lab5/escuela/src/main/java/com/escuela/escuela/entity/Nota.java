package com.escuela.escuela.entity;

public class Nota {
    private int idNota;
    private int idDetalle;
    private int idEvaluacion;
    private double nota;

    public Nota() {}

    public Nota(int idNota, int idDetalle, int idEvaluacion, double nota) {
        this.idNota = idNota;
        this.idDetalle = idDetalle;
        this.idEvaluacion = idEvaluacion;
        this.nota = nota;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
