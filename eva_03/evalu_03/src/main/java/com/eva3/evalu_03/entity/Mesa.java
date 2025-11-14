package com.eva3.evalu_03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "mesas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa")
    private Long idMesa;

    @NotNull
    @Column(unique = true, nullable = false)
    private Integer numero;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoMesa estado = EstadoMesa.DISPONIBLE;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos;

    public enum EstadoMesa {
        DISPONIBLE,
        OCUPADA,
        RESERVADA,
        MANTENIMIENTO
    }
}
