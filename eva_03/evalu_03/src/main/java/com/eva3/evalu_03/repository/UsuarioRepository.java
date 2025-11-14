package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    List<Usuario> findByEstado(Boolean estado);
    Boolean existsByNombreUsuario(String nombreUsuario);
}
