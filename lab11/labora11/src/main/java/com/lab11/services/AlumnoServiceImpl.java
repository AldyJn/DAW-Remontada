package com.lab11.services;

import com.lab11.domain.entities.Alumno;
import com.lab11.domain.persistence.AlumnoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoDao alumnoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listar() {
        return alumnoDao.findAll();
    }

    @Override
    @Transactional
    public void grabar(Alumno alumno) {
        alumnoDao.save(alumno);
    }

    @Override
    @Transactional(readOnly = true)
    public Alumno buscar(Integer id) {
        return alumnoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        alumnoDao.deleteById(id);
    }
}
