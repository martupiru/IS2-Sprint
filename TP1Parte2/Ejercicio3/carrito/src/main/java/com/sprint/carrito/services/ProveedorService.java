package com.sprint.carrito.services;

import com.sprint.carrito.entities.Proveedor;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.repositories.ProveedorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService extends BaseService<Proveedor, String> {
    protected ProveedorService(ProveedorRepository repository) {
        super(repository);
    }

    protected void validar(Proveedor prov) throws ErrorServiceException {
        try {
            if (prov.getNombre() == null || prov.getNombre().trim().isEmpty()) {
                throw new ErrorServiceException("El nombre no puede estar vacío");
            }
        }catch(ErrorServiceException e) {
            throw e;
        }catch(Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}
