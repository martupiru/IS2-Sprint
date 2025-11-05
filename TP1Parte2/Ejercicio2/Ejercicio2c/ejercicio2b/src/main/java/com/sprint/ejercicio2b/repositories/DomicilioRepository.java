package com.sprint.ejercicio2b.repositories;

import com.sprint.ejercicio2b.entities.Domicilio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomicilioRepository extends BaseRepository<Domicilio, Long> {

    // Búsqueda por calle
    @Query("SELECT d FROM Domicilio d WHERE d.calle LIKE %:calle%")
    List<Domicilio> searchByCalle(@Param("calle") String calle);

    @Query("SELECT d FROM Domicilio d WHERE d.calle LIKE %:calle%")
    Page<Domicilio> searchByCalle(@Param("calle") String calle, Pageable pageable);

    // Búsqueda por localidad
    @Query("SELECT d FROM Domicilio d JOIN d.localidad l WHERE l.denominacion LIKE %:localidad%")
    List<Domicilio> searchByLocalidad(@Param("localidad") String localidad);

    @Query("SELECT d FROM Domicilio d JOIN d.localidad l WHERE l.denominacion LIKE %:localidad%")
    Page<Domicilio> searchByLocalidad(@Param("localidad") String localidad, Pageable pageable);
}