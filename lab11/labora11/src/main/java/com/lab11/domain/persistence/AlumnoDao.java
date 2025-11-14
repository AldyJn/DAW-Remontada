package com.lab11.domain.persistence;

import com.lab11.domain.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoDao extends JpaRepository<Alumno, Integer> {
}
