package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.PlatoInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoInsumoRepository extends JpaRepository<PlatoInsumo, Long> {
    List<PlatoInsumo> findByPlatoIdPlato(Long idPlato);
    List<PlatoInsumo> findByInsumoIdInsumo(Long idInsumo);
}
