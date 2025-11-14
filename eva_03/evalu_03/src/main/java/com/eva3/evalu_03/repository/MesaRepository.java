package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Optional<Mesa> findByNumero(Integer numero);
    List<Mesa> findByEstado(Mesa.EstadoMesa estado);
    List<Mesa> findByCapacidadGreaterThanEqual(Integer capacidad);
}
