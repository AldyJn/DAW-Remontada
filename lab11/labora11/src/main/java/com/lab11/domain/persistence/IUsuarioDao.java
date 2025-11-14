package com.lab11.domain.persistence;

import com.lab11.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {
    public Usuario findByUsername(String username);
}
