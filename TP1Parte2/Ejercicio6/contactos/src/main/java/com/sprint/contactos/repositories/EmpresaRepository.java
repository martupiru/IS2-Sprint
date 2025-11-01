package com.sprint.contactos.repositories;

import com.sprint.contactos.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends BaseRepository<Empresa, String> {
    Optional<Empresa> findByNombreIgnoreCase(String nombre);
}
