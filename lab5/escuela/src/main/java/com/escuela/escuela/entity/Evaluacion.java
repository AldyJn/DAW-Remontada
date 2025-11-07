package com.escuela.escuela.entity;

public class Evaluacion {
    private int idEvaluacion;
    private String idCurso;
    private String nombre;
    private double peso;

    public Evaluacion() {}

    public Evaluacion(int idEvaluacion, String idCurso, String nombre, double peso) {
        this.idEvaluacion = idEvaluacion;
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.peso = peso;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
