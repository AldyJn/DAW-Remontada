package com.eva3.evalu_03.repository;

import com.eva3.evalu_03.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDni(String dni);
    List<Cliente> findByEstado(Boolean estado);
    List<Cliente> findByNombresContainingOrApellidosContaining(String nombres, String apellidos);
}
