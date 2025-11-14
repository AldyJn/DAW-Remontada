package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
    List<Plato> findByTipo(Plato.TipoPlato tipo);
    List<Plato> findByEstado(Boolean estado);
    List<Plato> findByNombreContaining(String nombre);
    List<Plato> findByTipoAndEstado(Plato.TipoPlato tipo, Boolean estado);
}
