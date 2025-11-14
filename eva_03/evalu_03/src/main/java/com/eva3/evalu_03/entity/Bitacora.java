package com.eva3.evalu_03.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    private Long idBitacora;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String usuario;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String accion;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String metodo;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "ip_cliente", length = 45)
    private String ipCliente;
}
