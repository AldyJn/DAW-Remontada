package com.eva3.evalu_03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "insumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumo")
    private Long idInsumo;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Size(max = 20)
    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal stock;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(name = "stock_minimo", nullable = false, precision = 10, scale = 2)
    private BigDecimal stockMinimo;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(name = "precio_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Column(nullable = false)
    private Boolean estado = true;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PlatoInsumo> platoInsumos;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetalleCompra> detallesCompra;
}
