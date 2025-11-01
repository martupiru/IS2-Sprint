package com.sprint.contactos.repositories;

import com.sprint.contactos.entities.ContactoTelefonico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ContactoTelefonicoRepository extends BaseRepository<ContactoTelefonico, String> {
    Optional<ContactoTelefonico> findByTelefono(String telefono);
}
