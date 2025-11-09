package com.lab10.domain.persistence;

import com.lab10.domain.entities.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaDao extends JpaRepository<Auditoria, Integer> {
}
