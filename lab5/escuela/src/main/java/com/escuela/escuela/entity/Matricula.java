package com.escuela.escuela.entity;

import java.util.Date;

public class Matricula {
    private int idMatricula;
    private String idAlumno;
    private int idPeriodo;
    private Date fechaMatricula;
    private int estado;

    public Matricula() {}

    public Matricula(int idMatricula, String idAlumno, int idPeriodo, Date fechaMatricula, int estado) {
        this.idMatricula = idMatricula;
        this.idAlumno = idAlumno;
        this.idPeriodo = idPeriodo;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Date getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
