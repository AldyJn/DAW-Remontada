package com.lab11.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "alumnos")
public class Alumno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotNull(message = "Los créditos son obligatorios")
    @Min(value = 0, message = "Los créditos no pueden ser negativos")
    @Max(value = 100, message = "Los créditos no pueden ser mayores a 100")
    private Integer creditos;

    public Alumno() {
    }

    public Alumno(Integer id, String nombre, Integer creditos) {
        this.id = id;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    private static final long serialVersionUID = 1L;
}
