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
    @Query("SELECT u FROM Usuario u WHERE u.cuenta = :cuenta AND u.clave = :clave AND u.eliminado = false")
    Optional<Usuario> login(@Param("cuenta") String cuenta, @Param("clave") String clave);
}
