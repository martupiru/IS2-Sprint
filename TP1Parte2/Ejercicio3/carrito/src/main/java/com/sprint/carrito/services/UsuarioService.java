package com.sprint.carrito.services;

import com.sprint.carrito.entities.Usuario;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BaseService<Usuario, String>{

    protected UsuarioService(UsuarioRepository repository) {
        super(repository);
    }

    protected void validar(Usuario user) throws ErrorServiceException {
        try {
            if (user.getNombre() == null || user.getNombre().trim().isEmpty()) {
                throw new ErrorServiceException("El nombre no puede estar vacío");
            }
            if (user.getClave() == null || user.getClave().trim().isEmpty()) {
                throw new ErrorServiceException("La clave no puede estar vacía");
            }
        }catch(ErrorServiceException e) {
            throw e;
        }catch(Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}
