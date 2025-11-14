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
@Table(name = "platos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plato")
    private Long idPlato;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoPlato tipo;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Size(max = 500)
    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Boolean estado = true;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PlatoInsumo> platoInsumos;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetallePedido> detalles;

    public enum TipoPlato {
        ENTRADA,
        FONDO,
        POSTRE,
        BEBIDA
    }
}
