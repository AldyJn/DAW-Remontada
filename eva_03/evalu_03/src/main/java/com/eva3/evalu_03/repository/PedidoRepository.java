package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
    List<Pedido> findByMesaIdMesa(Long idMesa);
    List<Pedido> findByClienteIdCliente(Long idCliente);
    List<Pedido> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT p FROM Pedido p WHERE p.estado IN ('PENDIENTE', 'EN_PREPARACION')")
    List<Pedido> findPedidosActivos();
}
