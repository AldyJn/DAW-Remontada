package com.lab8.servicios;

import com.lab8.modelo.entidades.Alumno;
import com.lab8.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository repository;

    @Override
    public List<Alumno> listar() {
        return repository.findAll();
    }

    @Override
    public void grabar(Alumno alumno) {
        repository.save(alumno);
    }

    @Override
    public Alumno buscar(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
