package com.eva3.evalu_03.service;

import com.eva3.evalu_03.aspect.Auditado;
import com.eva3.evalu_03.entity.*;
import com.eva3.evalu_03.repository.DetallePedidoRepository;
import com.eva3.evalu_03.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final MesaService mesaService;
    private final PlatoService platoService;

    @Auditado(accion = "CREAR_PEDIDO", descripcion = "Crear nuevo pedido")
    public Pedido crear(Pedido pedido) {
        // Cambiar estado de la mesa a OCUPADA
        Mesa mesa = mesaService.obtenerPorId(pedido.getMesa().getIdMesa());
        mesaService.cambiarEstado(mesa.getIdMesa(), Mesa.EstadoMesa.OCUPADA);

        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEstado(Pedido.EstadoPedido.PENDIENTE);

        // Establecer relación bidireccional con los detalles
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(detalle -> detalle.setPedido(pedido));
        }

        return pedidoRepository.save(pedido);
    }

    @Auditado(accion = "AGREGAR_DETALLE_PEDIDO", descripcion = "Agregar plato al pedido")
    public DetallePedido agregarDetalle(Long idPedido, Long idPlato, Integer cantidad) {
        Pedido pedido = obtenerPorId(idPedido);
        Plato plato = platoService.obtenerPorId(idPlato);

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setPlato(plato);
        detalle.setCantidad(cantidad);
        detalle.setSubtotal(plato.getPrecio().doubleValue() * cantidad);

        return detallePedidoRepository.save(detalle);
    }

    @Auditado(accion = "CAMBIAR_ESTADO_PEDIDO", descripcion = "Cambiar estado del pedido")
    public Pedido cambiarEstado(Long id, Pedido.EstadoPedido nuevoEstado) {
        Pedido pedido = obtenerPorId(id);
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    @Auditado(accion = "CERRAR_PEDIDO", descripcion = "Cerrar pedido y liberar mesa")
    public Pedido cerrar(Long id) {
        Pedido pedido = obtenerPorId(id);
        pedido.setEstado(Pedido.EstadoPedido.CERRADO);

        // Liberar la mesa
        Mesa mesa = pedido.getMesa();
        mesaService.cambiarEstado(mesa.getIdMesa(), Mesa.EstadoMesa.DISPONIBLE);

        return pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarActivos() {
        return pedidoRepository.findPedidosActivos();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorMesa(Long idMesa) {
        return pedidoRepository.findByMesaIdMesa(idMesa);
    }

    @Transactional(readOnly = true)
    public List<DetallePedido> obtenerDetalles(Long idPedido) {
        return detallePedidoRepository.findByPedidoIdPedido(idPedido);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularTotal(Long idPedido) {
        List<DetallePedido> detalles = obtenerDetalles(idPedido);
        double total = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
        return BigDecimal.valueOf(total);
    }
}
