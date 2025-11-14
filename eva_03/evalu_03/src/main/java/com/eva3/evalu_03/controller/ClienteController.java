package com.eva3.evalu_03.controller;

import com.eva3.evalu_03.entity.Cliente;
import com.eva3.evalu_03.service.ClienteService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gestión de clientes")
@SecurityRequirement(name = "bearer-key")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Crear cliente", description = "Registrar un nuevo cliente")
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        return new ResponseEntity<>(clienteService.crear(cliente), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Actualizar cliente", description = "Actualizar datos de un cliente")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.actualizar(id, cliente));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar cliente", description = "Eliminar un cliente (soft delete)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Obtener cliente por ID", description = "Consultar un cliente por su ID")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @GetMapping("/dni/{dni}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Obtener cliente por DNI", description = "Consultar un cliente por su DNI")
    public ResponseEntity<Cliente> obtenerPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(clienteService.obtenerPorDni(dni));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Listar todos los clientes", description = "Obtener listado completo de clientes")
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Listar clientes activos", description = "Obtener listado de clientes activos")
    public ResponseEntity<List<Cliente>> listarActivos() {
        return ResponseEntity.ok(clienteService.listarActivos());
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Buscar clientes", description = "Buscar clientes por nombre o apellido")
    public ResponseEntity<List<Cliente>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(clienteService.buscarPorNombre(nombre));
    }
}
