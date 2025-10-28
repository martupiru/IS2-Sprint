package com.gimnasio.gimnasio.repositories;


import com.gimnasio.gimnasio.entities.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, String> {

    @Query(value = "SELECT * FROM sucursales WHERE eliminado = false", nativeQuery = true)
    List<Sucursal> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM sucursale WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Sucursal> findByIdAndEliminadoFalse(@Param("id") String id);

    @Query(value = "SELECT * FROM sucursales WHERE nombre = :nombre AND eliminado = false", nativeQuery = true)
    Optional<Sucursal> findByNombreAndEliminadoFalse(@Param("nombre") String nombre);

}
