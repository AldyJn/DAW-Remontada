package com.eva3.evalu_03.service;

import com.eva3.evalu_03.aspect.Auditado;
import com.eva3.evalu_03.entity.Cliente;
import com.eva3.evalu_03.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Auditado(accion = "CREAR_CLIENTE", descripcion = "Registrar nuevo cliente")
    public Cliente crear(Cliente cliente) {
        if (clienteRepository.findByDni(cliente.getDni()).isPresent()) {
            throw new RuntimeException("Ya existe un cliente con el DNI: " + cliente.getDni());
        }
        return clienteRepository.save(cliente);
    }

    @Auditado(accion = "ACTUALIZAR_CLIENTE", descripcion = "Actualizar datos de cliente")
    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        Cliente cliente = obtenerPorId(id);
        cliente.setNombres(clienteActualizado.getNombres());
        cliente.setApellidos(clienteActualizado.getApellidos());
        cliente.setTelefono(clienteActualizado.getTelefono());
        cliente.setCorreo(clienteActualizado.getCorreo());
        cliente.setEstado(clienteActualizado.getEstado());
        return clienteRepository.save(cliente);
    }

    @Auditado(accion = "ELIMINAR_CLIENTE", descripcion = "Eliminar cliente (cambio de estado)")
    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        cliente.setEstado(false);
        clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public Cliente obtenerPorDni(String dni) {
        return clienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con DNI: " + dni));
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarActivos() {
        return clienteRepository.findByEstado(true);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombresContainingOrApellidosContaining(nombre, nombre);
    }
}
