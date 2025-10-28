package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Provincia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, String> {
    @Query(value = "SELECT * FROM provincias WHERE eliminado = false", nativeQuery = true)
    List<Provincia> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM provincias WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Provincia> findByIdAndEliminadoFalse(@Param("id") String id);

    @Query(value = "SELECT * FROM provincias WHERE nombre = :nombre AND eliminado = false", nativeQuery = true)
    Optional<Provincia> findByNombreAndEliminadoFalse(@Param("nombre") String nombre);

}
