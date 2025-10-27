package com.sprint.consultorio.repositories;

import com.sprint.consultorio.entities.Medico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends BaseRepository<Medico, Long> {
    // metodos especificos de medico

    @Query("SELECT e FROM Medico e WHERE e.eliminado = false")
    List<Medico> findAllActivos();
}
