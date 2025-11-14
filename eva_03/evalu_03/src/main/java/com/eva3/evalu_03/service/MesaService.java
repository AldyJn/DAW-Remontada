package com.eva3.evalu_03.service;

import com.eva3.evalu_03.aspect.Auditado;
import com.eva3.evalu_03.entity.Mesa;
import com.eva3.evalu_03.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MesaService {

    private final MesaRepository mesaRepository;

    @Auditado(accion = "CREAR_MESA", descripcion = "Registrar nueva mesa")
    public Mesa crear(Mesa mesa) {
        if (mesaRepository.findByNumero(mesa.getNumero()).isPresent()) {
            throw new RuntimeException("Ya existe una mesa con el número: " + mesa.getNumero());
        }
        return mesaRepository.save(mesa);
    }

    @Auditado(accion = "ACTUALIZAR_MESA", descripcion = "Actualizar mesa")
    public Mesa actualizar(Long id, Mesa mesaActualizada) {
        Mesa mesa = obtenerPorId(id);
        mesa.setCapacidad(mesaActualizada.getCapacidad());
        mesa.setEstado(mesaActualizada.getEstado());
        return mesaRepository.save(mesa);
    }

    @Auditado(accion = "CAMBIAR_ESTADO_MESA", descripcion = "Cambiar estado de mesa")
    public Mesa cambiarEstado(Long id, Mesa.EstadoMesa nuevoEstado) {
        Mesa mesa = obtenerPorId(id);
        mesa.setEstado(nuevoEstado);
        return mesaRepository.save(mesa);
    }

    @Auditado(accion = "ELIMINAR_MESA", descripcion = "Eliminar mesa")
    public void eliminar(Long id) {
        mesaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Mesa obtenerPorId(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Mesa> listarPorEstado(Mesa.EstadoMesa estado) {
        return mesaRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Mesa> listarDisponibles() {
        return mesaRepository.findByEstado(Mesa.EstadoMesa.DISPONIBLE);
    }
}
