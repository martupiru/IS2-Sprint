package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Departamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, String> {

    @Query(value = "SELECT * FROM departamentos WHERE eliminado = false", nativeQuery = true)
    List<Departamento> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM departamentos WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Departamento> findByIdAndEliminadoFalse(@Param("id") String id);

    @Query(value = "SELECT * FROM departamentos WHERE nombre = :nombre AND eliminado = false", nativeQuery = true)
    Optional<Departamento> findByNombreAndEliminadoFalse(@Param("nombre") String nombre);

}