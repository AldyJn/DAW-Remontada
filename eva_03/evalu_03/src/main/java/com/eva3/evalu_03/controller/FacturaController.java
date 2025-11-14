package com.eva3.evalu_03.controller;

import com.eva3.evalu_03.entity.DetalleFactura;
import com.eva3.evalu_03.entity.Factura;
import com.eva3.evalu_03.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
@Tag(name = "Facturas", description = "Gestión de facturación")
@SecurityRequirement(name = "bearer-key")
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO')")
    @Operation(summary = "Crear factura", description = "Generar factura de un pedido")
    public ResponseEntity<Factura> crear(
            @RequestParam Long idPedido,
            @RequestParam Factura.MetodoPago metodoPago) {
        return new ResponseEntity<>(facturaService.crear(idPedido, metodoPago), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/anular")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Anular factura", description = "Anular una factura existente")
    public ResponseEntity<Void> anular(@PathVariable Long id) {
        facturaService.anular(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO')")
    @Operation(summary = "Obtener factura por ID", description = "Consultar una factura por su ID")
    public ResponseEntity<Factura> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO')")
    @Operation(summary = "Listar todas las facturas", description = "Obtener listado completo de facturas")
    public ResponseEntity<List<Factura>> listarTodas() {
        return ResponseEntity.ok(facturaService.listarTodas());
    }

    @GetMapping("/activas")
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO')")
    @Operation(summary = "Listar facturas activas", description = "Obtener facturas no anuladas")
    public ResponseEntity<List<Factura>> listarActivas() {
        return ResponseEntity.ok(facturaService.listarActivas());
    }

    @GetMapping("/{id}/detalles")
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO')")
    @Operation(summary = "Obtener detalles de factura", description = "Consultar los detalles de una factura")
    public ResponseEntity<List<DetalleFactura>> obtenerDetalles(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerDetalles(id));
    }

    @GetMapping("/ventas/total")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Calcular total de ventas", description = "Obtener el total de ventas en un periodo")
    public ResponseEntity<Double> calcularTotalVentas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(facturaService.calcularTotalVentas(inicio, fin));
    }
}
