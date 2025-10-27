package com.sprint.consultorio.repositories;

import com.sprint.consultorio.entities.HistoriaClinica;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriaClinicaRepository extends BaseRepository<HistoriaClinica, Long> {
    boolean existsByPaciente_Id(Long pacienteId);
}
