package com.eva3.evalu_03.service;

import com.eva3.evalu_03.aspect.Auditado;
import com.eva3.evalu_03.entity.Insumo;
import com.eva3.evalu_03.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InsumoService {

    private final InsumoRepository insumoRepository;

    @Auditado(accion = "CREAR_INSUMO", descripcion = "Registrar nuevo insumo")
    public Insumo crear(Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    @Auditado(accion = "ACTUALIZAR_INSUMO", descripcion = "Actualizar insumo")
    public Insumo actualizar(Long id, Insumo insumoActualizado) {
        Insumo insumo = obtenerPorId(id);
        insumo.setNombre(insumoActualizado.getNombre());
        insumo.setUnidadMedida(insumoActualizado.getUnidadMedida());
        insumo.setStock(insumoActualizado.getStock());
        insumo.setStockMinimo(insumoActualizado.getStockMinimo());
        insumo.setPrecioCompra(insumoActualizado.getPrecioCompra());
        insumo.setEstado(insumoActualizado.getEstado());
        return insumoRepository.save(insumo);
    }

    @Auditado(accion = "ELIMINAR_INSUMO", descripcion = "Eliminar insumo")
    public void eliminar(Long id) {
        Insumo insumo = obtenerPorId(id);
        insumo.setEstado(false);
        insumoRepository.save(insumo);
    }

    @Transactional(readOnly = true)
    public Insumo obtenerPorId(Long id) {
        return insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Insumo> listarTodos() {
        return insumoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Insumo> listarActivos() {
        return insumoRepository.findByEstado(true);
    }

    @Transactional(readOnly = true)
    public List<Insumo> listarConStockBajo() {
        return insumoRepository.findInsumosConStockBajo();
    }
}
