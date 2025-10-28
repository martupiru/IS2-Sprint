package com.gimnasio.gimnasio.repositories;


import com.gimnasio.gimnasio.entities.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, String> {

    @Query(value = "SELECT * FROM localidades WHERE eliminado = false", nativeQuery = true)
    List<Localidad> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM localidades WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Localidad> findByIdAndEliminadoFalse(@Param("id") String id);

    @Query(value = "SELECT * FROM localidades WHERE nombre = :nombre AND eliminado = false", nativeQuery = true)
    Optional<Localidad> findByNombreAndEliminadoFalse(@Param("nombre") String nombre);

    //buscar por codigo postal
    @Query(value = "SELECT * FROM localidades WHERE codigoPostal = :codigoPostal AND eliminado = false", nativeQuery = true)
    Optional<Localidad> findByCodigoPostalAndEliminadoFalse(@Param("codigoPostal") String codigoPostal);

}