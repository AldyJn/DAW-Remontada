package com.eva3.evalu_03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;

    @NotBlank
    @Size(min = 11, max = 11)
    @Column(unique = true, nullable = false, length = 11)
    private String ruc;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String nombre;

    @Size(max = 15)
    @Column(length = 15)
    private String telefono;

    @Email
    @Size(max = 100)
    @Column(length = 100)
    private String correo;

    @Size(max = 300)
    @Column(length = 300)
    private String direccion;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Compra> compras;
}
