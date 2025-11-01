package com.sprint.contactos.repositories;

import com.sprint.contactos.entities.Contacto;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends BaseRepository<Contacto, String> {

}
