package com.sprint.consultorio.repositories;

import com.sprint.consultorio.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    // metodos especificos de usuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    @Query("SELECT e FROM Usuario e WHERE e.eliminado = false")
    List<Usuario> findAllActivos();
}
