package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
    List<Bitacora> findByUsuario(String usuario);
    List<Bitacora> findByAccion(String accion);
    List<Bitacora> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Bitacora> findByUsuarioAndFechaBetween(String usuario, LocalDateTime inicio, LocalDateTime fin);
}
