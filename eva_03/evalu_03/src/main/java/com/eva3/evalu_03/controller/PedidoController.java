package com.eva3.evalu_03.controller;

import com.eva3.evalu_03.entity.DetallePedido;
import com.eva3.evalu_03.entity.Pedido;
import com.eva3.evalu_03.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gestión de pedidos")
@SecurityRequirement(name = "bearer-key")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Crear pedido", description = "Crear un nuevo pedido")
    public ResponseEntity<Pedido> crear(@Valid @RequestBody Pedido pedido) {
        return new ResponseEntity<>(pedidoService.crear(pedido), HttpStatus.CREATED);
    }

    @PostMapping("/{idPedido}/detalles")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Agregar detalle al pedido", description = "Agregar un plato al pedido")
    public ResponseEntity<DetallePedido> agregarDetalle(
            @PathVariable Long idPedido,
            @RequestParam Long idPlato,
            @RequestParam Integer cantidad) {
        return new ResponseEntity<>(pedidoService.agregarDetalle(idPedido, idPlato, cantidad), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'COCINERO')")
    @Operation(summary = "Cambiar estado del pedido", description = "Actualizar el estado de un pedido")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id, @RequestParam Pedido.EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, estado));
    }

    @PatchMapping("/{id}/cerrar")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    @Operation(summary = "Cerrar pedido", description = "Cerrar un pedido y liberar la mesa")
    public ResponseEntity<Pedido> cerrar(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.cerrar(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'COCINERO', 'CAJERO')")
    @Operation(summary = "Obtener pedido por ID", description = "Consultar un pedido por su ID")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'COCINERO')")
    @Operation(summary = "Listar todos los pedidos", description = "Obtener listado completo de pedidos")
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'COCINERO')")
    @Operation(summary = "Listar pedidos activos", description = "Obtener pedidos pendientes o en preparación")
    public ResponseEntity<List<Pedido>> listarActivos() {
        return ResponseEntity.ok(pedidoService.listarActivos());
    }

    @GetMapping("/{id}/detalles")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'COCINERO', 'CAJERO')")
    @Operation(summary = "Obtener detalles del pedido", description = "Consultar los detalles de un pedido")
    public ResponseEntity<List<DetallePedido>> obtenerDetalles(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerDetalles(id));
    }

    @GetMapping("/{id}/total")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'CAJERO')")
    @Operation(summary = "Calcular total del pedido", description = "Obtener el total a pagar del pedido")
    public ResponseEntity<BigDecimal> calcularTotal(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.calcularTotal(id));
    }
}
