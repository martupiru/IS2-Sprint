package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.Usuario;

import java.util.Optional;

public interface UsuarioService extends BaseService<Usuario, Long> {
    // Interfaz para el método que usará el login
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    Optional<Usuario> login(String nombreUsuario, String clave);
}
