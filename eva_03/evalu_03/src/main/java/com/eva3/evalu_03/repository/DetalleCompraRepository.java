package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
    List<DetalleCompra> findByCompraIdCompra(Long idCompra);
    List<DetalleCompra> findByInsumoIdInsumo(Long idInsumo);
}
