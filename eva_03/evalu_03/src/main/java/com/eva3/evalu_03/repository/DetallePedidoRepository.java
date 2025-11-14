package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedidoIdPedido(Long idPedido);
    List<DetallePedido> findByPlatoIdPlato(Long idPlato);
}
