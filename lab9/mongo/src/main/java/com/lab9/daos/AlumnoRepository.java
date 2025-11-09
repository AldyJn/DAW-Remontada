package com.lab9.daos;

import com.lab9.modelo.documents.Alumno;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlumnoRepository extends MongoRepository<Alumno, String> {
}
