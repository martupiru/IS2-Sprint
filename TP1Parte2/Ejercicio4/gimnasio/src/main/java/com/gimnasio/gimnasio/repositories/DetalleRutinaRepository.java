package com.gimnasio.gimnasio.repositories;


import com.gimnasio.gimnasio.entities.DetalleRutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleRutinaRepository extends JpaRepository<DetalleRutina, String> {

    @Query(value = "SELECT * FROM detalle_rutinas WHERE eliminado = false", nativeQuery = true)
    List<DetalleRutina> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM detalle_rutinas WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<DetalleRutina> findByIdAndEliminadoFalse(@Param("id") String id);

}
