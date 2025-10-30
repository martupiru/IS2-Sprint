package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Departamento;
import com.gimnasio.gimnasio.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String> {

    @Query(value = "SELECT * FROM empresas WHERE eliminado = false", nativeQuery = true)
    List<Empresa> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM empresas WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Empresa> findByIdAndEliminadoFalse(@Param("id") String id);

    @Query(value = "SELECT * FROM empresas WHERE nombre = :nombre AND eliminado = false", nativeQuery = true)
    Optional<Empresa> findByNombreAndEliminadoFalse(@Param("nombre") String nombre);

}