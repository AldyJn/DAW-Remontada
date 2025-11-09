package com.lab9;

import com.lab9.modelo.documents.Alumno;
import com.lab9.daos.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (alumnoRepository.count() == 0) {
            alumnoRepository.save(new Alumno("Juan", "Perez", "juan.perez@email.com"));
            alumnoRepository.save(new Alumno("Maria", "Garcia", "maria.garcia@email.com"));
            alumnoRepository.save(new Alumno("Carlos", "Lopez", "carlos.lopez@email.com"));
            alumnoRepository.save(new Alumno("Ana", "Martinez", "ana.martinez@email.com"));
            alumnoRepository.save(new Alumno("Luis", "Rodriguez", "luis.rodriguez@email.com"));

            System.out.println("Datos de prueba cargados exitosamente");
        }
    }
}
