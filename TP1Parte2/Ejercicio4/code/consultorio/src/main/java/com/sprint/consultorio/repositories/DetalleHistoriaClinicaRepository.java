package com.sprint.consultorio.repositories;

import com.sprint.consultorio.entities.DetalleHistoriaClinica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleHistoriaClinicaRepository extends BaseRepository<DetalleHistoriaClinica, Long> {
    @Query("SELECT e FROM DetalleHistoriaClinica e WHERE e.eliminado = false")
    List<DetalleHistoriaClinica> findAllActivos();
}

