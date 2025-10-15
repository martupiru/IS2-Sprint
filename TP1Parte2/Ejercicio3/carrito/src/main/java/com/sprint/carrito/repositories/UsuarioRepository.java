package com.sprint.carrito.repositories;

import com.sprint.carrito.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario,String>{
    // Buscar por nombre y clave NO eliminado
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.clave = :clave AND u.eliminado = false")
    Optional<Usuario> login(@Param("nombre") String cuenta, @Param("clave") String clave);
}
