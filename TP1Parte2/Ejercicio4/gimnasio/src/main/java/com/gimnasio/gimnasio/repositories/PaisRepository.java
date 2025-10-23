package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Pais;
import com.gimnasio.gimnasio.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, String> {
    @Query(value = "SELECT * FROM paises WHERE eliminado = false", nativeQuery = true)
    List<Pais> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM paises WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Pais> findByIdAndEliminadoFalse(@Param("id") String id);

    @Query(value = "SELECT * FROM paises WHERE nombre = :nombre AND eliminado = false", nativeQuery = true)
    Optional<Pais> findByNombreAndEliminadoFalse(@Param("nombre") String nombre);
}
