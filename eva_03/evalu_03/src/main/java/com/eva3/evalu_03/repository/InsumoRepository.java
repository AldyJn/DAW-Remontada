package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    List<Insumo> findByEstado(Boolean estado);
    List<Insumo> findByNombreContaining(String nombre);

    @Query("SELECT i FROM Insumo i WHERE i.stock <= i.stockMinimo")
    List<Insumo> findInsumosConStockBajo();
}
