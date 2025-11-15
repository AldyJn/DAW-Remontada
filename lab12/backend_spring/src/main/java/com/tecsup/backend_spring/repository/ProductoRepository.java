package com.tecsup.backend_spring.repository;

import com.tecsup.backend_spring.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
