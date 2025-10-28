package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Departamento;
import com.gimnasio.gimnasio.entities.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, String> {

    @Query(value = "SELECT * FROM direcciones WHERE eliminado = false", nativeQuery = true)
    List<Direccion> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM direcciones WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Direccion> findByIdAndEliminadoFalse(@Param("id") String id);

    //buscar por calle y numeracion
    @Query(value = "SELECT * FROM direcciones WHERE calle = :calle AND numeracion = :numeracion AND eliminado = false", nativeQuery = true)
    Optional<Direccion> findByCalleAndNumeracionAndEliminadoFalse(@Param("calle") String calle, @Param("numeracion") String numeracion);

}