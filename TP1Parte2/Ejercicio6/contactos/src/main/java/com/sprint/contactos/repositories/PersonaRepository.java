package com.sprint.contactos.repositories;

import com.sprint.contactos.entities.Persona;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends BaseRepository<Persona, String> {

}
