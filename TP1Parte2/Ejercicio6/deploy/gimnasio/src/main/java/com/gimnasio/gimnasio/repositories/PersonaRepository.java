package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {

    @Query(value = "SELECT * FROM personas WHERE eliminado = false", nativeQuery = true)
    List<Persona> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM personas WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Persona> findByIdAndEliminadoFalse(@Param("id") String id);

}