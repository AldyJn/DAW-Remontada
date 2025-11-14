package com.eva3.evalu_03.service;

import com.eva3.evalu_03.aspect.Auditado;
import com.eva3.evalu_03.entity.*;
import com.eva3.evalu_03.repository.DetalleFacturaRepository;
import com.eva3.evalu_03.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final DetalleFacturaRepository detalleFacturaRepository;
    private final PedidoService pedidoService;

    @Auditado(accion = "CREAR_FACTURA", descripcion = "Generar factura de pedido")
    public Factura crear(Long idPedido, Factura.MetodoPago metodoPago) {
        Pedido pedido = pedidoService.obtenerPorId(idPedido);

        if (pedido.getEstado() != Pedido.EstadoPedido.SERVIDO) {
            throw new RuntimeException("El pedido debe estar en estado SERVIDO para generar la factura");
        }

        // Verificar si ya existe factura para este pedido
        if (facturaRepository.findByPedidoIdPedido(idPedido).isPresent()) {
            throw new RuntimeException("Ya existe una factura para este pedido");
        }

        BigDecimal total = pedidoService.calcularTotal(idPedido);

        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setFechaEmision(LocalDateTime.now());
        factura.setTotal(total);
        factura.setMetodoPago(metodoPago);
        factura.setEstado(true);

        Factura facturaSaved = facturaRepository.save(factura);

        // Crear detalles de factura basados en el pedido
        List<DetallePedido> detallesPedido = pedidoService.obtenerDetalles(idPedido);
        for (DetallePedido detallePedido : detallesPedido) {
            DetalleFactura detalleFactura = new DetalleFactura();
            detalleFactura.setFactura(facturaSaved);
            detalleFactura.setConcepto(detallePedido.getCantidad() + "x " + detallePedido.getPlato().getNombre());
            detalleFactura.setMonto(BigDecimal.valueOf(detallePedido.getSubtotal()));
            detalleFacturaRepository.save(detalleFactura);
        }

        // Cerrar el pedido
        pedidoService.cerrar(idPedido);

        return facturaSaved;
    }

    @Auditado(accion = "ANULAR_FACTURA", descripcion = "Anular factura")
    public void anular(Long id) {
        Factura factura = obtenerPorId(id);
        factura.setEstado(false);
        facturaRepository.save(factura);
    }

    @Transactional(readOnly = true)
    public Factura obtenerPorId(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Factura> listarTodas() {
        return facturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Factura> listarActivas() {
        return facturaRepository.findByEstado(true);
    }

    @Transactional(readOnly = true)
    public List<DetalleFactura> obtenerDetalles(Long idFactura) {
        return detalleFacturaRepository.findByFacturaIdFactura(idFactura);
    }

    @Transactional(readOnly = true)
    public Double calcularTotalVentas(LocalDateTime inicio, LocalDateTime fin) {
        Double total = facturaRepository.calcularTotalVentas(inicio, fin);
        return total != null ? total : 0.0;
    }
}
