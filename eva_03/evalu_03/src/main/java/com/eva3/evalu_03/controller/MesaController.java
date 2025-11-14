package com.eva3.evalu_03.controller;

import com.eva3.evalu_03.entity.Mesa;
import com.eva3.evalu_03.service.MesaService;
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
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
@Tag(name = "Mesas", description = "Gestión de mesas del restaurante")
@SecurityRequirement(name = "bearer-key")
public class MesaController {

    private final MesaService mesaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear mesa", description = "Registrar una nueva mesa")
    public ResponseEntity<Mesa> crear(@Valid @RequestBody Mesa mesa) {
        return new ResponseEntity<>(mesaService.crear(mesa), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar mesa", description = "Actualizar datos de una mesa")
    public ResponseEntity<Mesa> actualizar(@PathVariable Long id, @Valid @RequestBody Mesa mesa) {
        return ResponseEntity.ok(mesaService.actualizar(id, mesa));
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Cambiar estado de mesa", description = "Cambiar el estado de una mesa")
    public ResponseEntity<Mesa> cambiarEstado(@PathVariable Long id, @RequestParam Mesa.EstadoMesa estado) {
        return ResponseEntity.ok(mesaService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar mesa", description = "Eliminar una mesa")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mesaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Obtener mesa por ID", description = "Consultar una mesa por su ID")
    public ResponseEntity<Mesa> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mesaService.obtenerPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Listar todas las mesas", description = "Obtener listado completo de mesas")
    public ResponseEntity<List<Mesa>> listarTodas() {
        return ResponseEntity.ok(mesaService.listarTodas());
    }

    @GetMapping("/disponibles")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Listar mesas disponibles", description = "Obtener listado de mesas disponibles")
    public ResponseEntity<List<Mesa>> listarDisponibles() {
        return ResponseEntity.ok(mesaService.listarDisponibles());
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Listar mesas por estado", description = "Obtener listado de mesas filtradas por estado")
    public ResponseEntity<List<Mesa>> listarPorEstado(@PathVariable Mesa.EstadoMesa estado) {
        return ResponseEntity.ok(mesaService.listarPorEstado(estado));
    }
}
