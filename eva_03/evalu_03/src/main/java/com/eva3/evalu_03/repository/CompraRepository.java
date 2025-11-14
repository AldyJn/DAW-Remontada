package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByProveedorIdProveedor(Long idProveedor);
    List<Compra> findByFechaCompraBetween(LocalDateTime inicio, LocalDateTime fin);
}
