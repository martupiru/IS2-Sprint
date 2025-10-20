package com.sprint.consultorio.repositories;

import com.sprint.consultorio.entities.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    // metodos especificos de usuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
