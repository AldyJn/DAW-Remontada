package com.lab10.domain.persistence;

import com.lab10.domain.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoDao extends JpaRepository<Alumno, Integer> {
}
