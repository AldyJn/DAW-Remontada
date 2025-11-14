package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByPedidoIdPedido(Long idPedido);
    List<Factura> findByEstado(Boolean estado);
    List<Factura> findByFechaEmisionBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Factura> findByMetodoPago(Factura.MetodoPago metodoPago);

    @Query("SELECT SUM(f.total) FROM Factura f WHERE f.fechaEmision BETWEEN :inicio AND :fin AND f.estado = true")
    Double calcularTotalVentas(LocalDateTime inicio, LocalDateTime fin);
}
