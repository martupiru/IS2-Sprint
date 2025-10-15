package com.sprint.carrito.services;

import com.sprint.carrito.entities.Usuario;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.repositories.UsuarioRepository;
import com.sprint.carrito.utils.HashForLogin;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService extends BaseService<Usuario, String> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository repository) {
        super(repository);
        this.usuarioRepository = repository;
    }

    @Override
    protected void validar(Usuario user) throws ErrorServiceException {
        if (user.getNombre() == null || user.getNombre().trim().isEmpty()) {
            throw new ErrorServiceException("El nombre no puede estar vacío");
        }
        if (user.getClave() == null || user.getClave().trim().isEmpty()) {
            throw new ErrorServiceException("La clave no puede estar vacía");
        }
    }

    @Override
    protected void preAlta(Usuario user) throws ErrorServiceException {
        String claveOriginal = user.getClave(); // Guardamos la clave original
        String claveHash = HashForLogin.hashClave(claveOriginal);

        // --- AÑADE ESTA LÍNEA PARA DEPURAR ---
        System.out.println("Clave Original: " + claveOriginal + " | Clave Hasheada: " + claveHash);

        user.setClave(claveHash);
    }

    public Optional<Usuario> login(String nombre, String password) {
        String claveHash = HashForLogin.hashClave(password);
        return usuarioRepository.login(nombre, claveHash);
    }
}
