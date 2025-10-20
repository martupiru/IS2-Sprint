package com.sprint.consultorio.repositories;

import com.sprint.consultorio.entities.Paciente;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends BaseRepository<Paciente, Long> {
    // metodos especificos de paciente
}
