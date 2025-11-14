package com.lab11.domain.persistence;

import com.lab11.domain.entities.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaDao extends JpaRepository<Auditoria, Long> {
}
