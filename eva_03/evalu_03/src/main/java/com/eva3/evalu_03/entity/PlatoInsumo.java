package com.eva3.evalu_03.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "plato_insumo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatoInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plato_insumo")
    private Long idPlatoInsumo;

    @ManyToOne
    @JoinColumn(name = "id_plato", nullable = false)
    @NotNull
    private Plato plato;

    @ManyToOne
    @JoinColumn(name = "id_insumo", nullable = false)
    @NotNull
    private Insumo insumo;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "cantidad_usada", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadUsada;
}
