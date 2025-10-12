package com.sprint.ejercicio2b.repositories;

import com.sprint.ejercicio2b.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
}
