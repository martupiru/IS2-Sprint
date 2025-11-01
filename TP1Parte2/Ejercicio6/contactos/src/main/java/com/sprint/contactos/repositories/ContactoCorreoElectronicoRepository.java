package com.sprint.contactos.repositories;

import com.sprint.contactos.entities.ContactoCorreoElectronico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ContactoCorreoElectronicoRepository extends BaseRepository<ContactoCorreoElectronico, String> {
    Optional<ContactoCorreoElectronico> findByEmailIgnoreCase(String email);
}


