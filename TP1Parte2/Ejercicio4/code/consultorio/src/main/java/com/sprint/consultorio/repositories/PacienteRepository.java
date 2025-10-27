package com.sprint.consultorio.repositories;

import com.sprint.consultorio.entities.Paciente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends BaseRepository<Paciente, Long> {
    @Query("SELECT e FROM Paciente e WHERE e.eliminado = false")
    List<Paciente> findAllActivos();
}
