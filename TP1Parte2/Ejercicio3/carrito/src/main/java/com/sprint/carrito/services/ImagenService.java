package com.sprint.carrito.services;

import com.sprint.carrito.entities.Imagen;
import com.sprint.carrito.error.ErrorServiceException;

import com.sprint.carrito.repositories.ImagenRepository;
import org.springframework.stereotype.Service;

@Service
public class ImagenService extends BaseService<Imagen, String> {

    protected ImagenService(ImagenRepository repository) {
        super(repository);
    }

    protected void validar(Imagen imagen) throws ErrorServiceException {
        try {
            if (imagen.getNombre() == null || imagen.getNombre().trim().isEmpty()) {
                throw new ErrorServiceException("El nombre no puede estar vacío");
            }
            if (imagen.getMime() == null || imagen.getMime().trim().isEmpty()) {
                throw new ErrorServiceException("El tipo MIME de la imagen no puede estar vacío.");
            }
            if (imagen.getContenido() == null || imagen.getContenido().length == 0) {
                throw new ErrorServiceException("La imagen debe tener contenido.");
            }
        }catch(ErrorServiceException e) {
            throw e;
        }catch(Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}
