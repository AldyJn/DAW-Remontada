package com.eva3.evalu_03.controller;

import com.eva3.evalu_03.entity.Plato;
import com.eva3.evalu_03.service.PlatoService;
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
@RequestMapping("/api/platos")
@RequiredArgsConstructor
@Tag(name = "Platos", description = "Gestión del menú de platos")
@SecurityRequirement(name = "bearer-key")
public class PlatoController {

    private final PlatoService platoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear plato", description = "Registrar un nuevo plato en el menú")
    public ResponseEntity<Plato> crear(@Valid @RequestBody Plato plato) {
        return new ResponseEntity<>(platoService.crear(plato), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar plato", description = "Actualizar datos de un plato")
    public ResponseEntity<Plato> actualizar(@PathVariable Long id, @Valid @RequestBody Plato plato) {
        return ResponseEntity.ok(platoService.actualizar(id, plato));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar plato", description = "Eliminar un plato (soft delete)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        platoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'COCINERO')")
    @Operation(summary = "Obtener plato por ID", description = "Consultar un plato por su ID")
    public ResponseEntity<Plato> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(platoService.obtenerPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'COCINERO')")
    @Operation(summary = "Listar todos los platos", description = "Obtener listado completo de platos")
    public ResponseEntity<List<Plato>> listarTodos() {
        return ResponseEntity.ok(platoService.listarTodos());
    }

    @GetMapping("/activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'COCINERO')")
    @Operation(summary = "Listar platos activos", description = "Obtener listado de platos activos")
    public ResponseEntity<List<Plato>> listarActivos() {
        return ResponseEntity.ok(platoService.listarActivos());
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'COCINERO')")
    @Operation(summary = "Listar platos por tipo", description = "Obtener platos filtrados por tipo")
    public ResponseEntity<List<Plato>> listarPorTipo(@PathVariable Plato.TipoPlato tipo) {
        return ResponseEntity.ok(platoService.listarPorTipo(tipo));
    }
}
