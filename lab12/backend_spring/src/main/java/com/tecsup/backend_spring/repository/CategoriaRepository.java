package com.tecsup.backend_spring.repository;

import com.tecsup.backend_spring.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
