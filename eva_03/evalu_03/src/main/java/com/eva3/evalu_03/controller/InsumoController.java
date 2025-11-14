package com.eva3.evalu_03.controller;

import com.eva3.evalu_03.entity.Insumo;
import com.eva3.evalu_03.service.InsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
@RequiredArgsConstructor
@Tag(name = "Insumos", description = "Gestión de insumos e inventario")
@SecurityRequirement(name = "bearer-key")
public class InsumoController {

    private final InsumoService insumoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear insumo", description = "Registrar un nuevo insumo")
    public ResponseEntity<Insumo> crear(@Valid @RequestBody Insumo insumo) {
        return new ResponseEntity<>(insumoService.crear(insumo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar insumo", description = "Actualizar datos de un insumo")
    public ResponseEntity<Insumo> actualizar(@PathVariable Long id, @Valid @RequestBody Insumo insumo) {
        return ResponseEntity.ok(insumoService.actualizar(id, insumo));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar insumo", description = "Eliminar un insumo (soft delete)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        insumoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    @Operation(summary = "Obtener insumo por ID", description = "Consultar un insumo por su ID")
    public ResponseEntity<Insumo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(insumoService.obtenerPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    @Operation(summary = "Listar todos los insumos", description = "Obtener listado completo de insumos")
    public ResponseEntity<List<Insumo>> listarTodos() {
        return ResponseEntity.ok(insumoService.listarTodos());
    }

    @GetMapping("/activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    @Operation(summary = "Listar insumos activos", description = "Obtener listado de insumos activos")
    public ResponseEntity<List<Insumo>> listarActivos() {
        return ResponseEntity.ok(insumoService.listarActivos());
    }

    @GetMapping("/stock-bajo")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar insumos con stock bajo", description = "Obtener insumos con stock menor al mínimo")
    public ResponseEntity<List<Insumo>> listarConStockBajo() {
        return ResponseEntity.ok(insumoService.listarConStockBajo());
    }
}
