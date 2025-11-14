package com.eva3.evalu_03.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_pedido")
    private Long idDetallePedido;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @JsonBackReference("pedido-detalles")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_plato")
    private Plato plato;

    @Min(1)
    private Integer cantidad;

    private Double subtotal;
}
