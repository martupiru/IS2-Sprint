package com.sprint.consultorio.repositories;

import com.sprint.consultorio.entities.Medico;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends BaseRepository<Medico, Long> {
    // metodos especificos de medico
}
