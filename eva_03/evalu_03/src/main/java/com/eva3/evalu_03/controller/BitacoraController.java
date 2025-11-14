package com.eva3.evalu_03.controller;

import com.eva3.evalu_03.entity.Bitacora;
import com.eva3.evalu_03.repository.BitacoraRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
@RequiredArgsConstructor
@Tag(name = "Bitácora", description = "Consulta de auditoría del sistema")
@SecurityRequirement(name = "bearer-key")
public class BitacoraController {

    private final BitacoraRepository bitacoraRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar toda la bitácora", description = "Obtener todos los registros de auditoría")
    public ResponseEntity<List<Bitacora>> listarTodos() {
        return ResponseEntity.ok(bitacoraRepository.findAll());
    }

    @GetMapping("/usuario/{usuario}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Consultar por usuario", description = "Obtener registros de un usuario específico")
    public ResponseEntity<List<Bitacora>> consultarPorUsuario(@PathVariable String usuario) {
        return ResponseEntity.ok(bitacoraRepository.findByUsuario(usuario));
    }

    @GetMapping("/accion/{accion}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Consultar por acción", description = "Obtener registros de una acción específica")
    public ResponseEntity<List<Bitacora>> consultarPorAccion(@PathVariable String accion) {
        return ResponseEntity.ok(bitacoraRepository.findByAccion(accion));
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Consultar por periodo", description = "Obtener registros en un rango de fechas")
    public ResponseEntity<List<Bitacora>> consultarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(bitacoraRepository.findByFechaBetween(inicio, fin));
    }
}
