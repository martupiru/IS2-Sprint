package com.sprint.carrito.services;

import com.sprint.carrito.entities.Articulo;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.repositories.ArticuloRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticuloService extends BaseService<Articulo, String> {

    protected ArticuloService(ArticuloRepository repository) {
        super(repository);
    }

    protected void validar(Articulo art) throws ErrorServiceException {
        try {
            if (art.getNombre() == null || art.getNombre().trim().isEmpty()) {
                throw new ErrorServiceException("El nombre no puede estar vacío");
            }
            if (art.getPrecio() == null) {
                throw new ErrorServiceException("El precio no puede estar vacío");
            } else if (art.getPrecio()<0) {
                throw new ErrorServiceException("Debe ingresar un valor positivo");
            }
        }catch(ErrorServiceException e) {
            throw e;
        }catch(Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    @Override
    protected void preAlta(Articulo art) throws ErrorServiceException {
        art.setId(null);
    }


}
