package com.sprint.part2ej1.repositories;

import com.sprint.part2ej1.entities.Persona;
import com.sprint.part2ej1.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // Lista los mensajes activos
    @Query(value = "SELECT * FROM usuarios WHERE eliminado = false", nativeQuery = true)
    List<Usuario> findAllByEliminadoFalse();

    // Encuentra un mensaje activo por id
    @Query(value = "SELECT * FROM usuarios WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Usuario> findByIdAndEliminadoFalse(@Param("id") String id);

    // Buscar por nombre de usuario
    @Query(value = "SELECT * FROM usuarios WHERE nombre_usuario = :nombreUsuario", nativeQuery = true)
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    // Buscar por nombre y clave NO eliminado
    @Query(value = "SELECT * FROM usuarios WHERE nombre_usuario = :nombreUsuario AND clave = :clave AND eliminado = false", nativeQuery = true)
    Optional<Usuario> login(@Param("nombreUsuario") String nombreUsuario,
                            @Param("clave") String clave);

    // Para buscar el id de persona de ese usuario
    @Query("SELECT u FROM Usuario u WHERE u.persona.id = :personaId")
    Optional<Usuario> findByPersonaId(@Param("personaId") String personaId);

    @Query("SELECT u FROM Usuario u WHERE u.persona = :persona")
    Optional<Usuario> findByPersona(@Param("persona") Persona persona);
}
