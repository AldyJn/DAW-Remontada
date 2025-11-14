package com.eva3.evalu_03.service;

import com.eva3.evalu_03.aspect.Auditado;
import com.eva3.evalu_03.entity.Plato;
import com.eva3.evalu_03.repository.PlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlatoService {

    private final PlatoRepository platoRepository;

    @Auditado(accion = "CREAR_PLATO", descripcion = "Registrar nuevo plato")
    public Plato crear(Plato plato) {
        return platoRepository.save(plato);
    }

    @Auditado(accion = "ACTUALIZAR_PLATO", descripcion = "Actualizar plato")
    public Plato actualizar(Long id, Plato platoActualizado) {
        Plato plato = obtenerPorId(id);
        plato.setNombre(platoActualizado.getNombre());
        plato.setTipo(platoActualizado.getTipo());
        plato.setPrecio(platoActualizado.getPrecio());
        plato.setDescripcion(platoActualizado.getDescripcion());
        plato.setEstado(platoActualizado.getEstado());
        return platoRepository.save(plato);
    }

    @Auditado(accion = "ELIMINAR_PLATO", descripcion = "Eliminar plato (cambio de estado)")
    public void eliminar(Long id) {
        Plato plato = obtenerPorId(id);
        plato.setEstado(false);
        platoRepository.save(plato);
    }

    @Transactional(readOnly = true)
    public Plato obtenerPorId(Long id) {
        return platoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Plato> listarTodos() {
        return platoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Plato> listarActivos() {
        return platoRepository.findByEstado(true);
    }

    @Transactional(readOnly = true)
    public List<Plato> listarPorTipo(Plato.TipoPlato tipo) {
        return platoRepository.findByTipoAndEstado(tipo, true);
    }
}
