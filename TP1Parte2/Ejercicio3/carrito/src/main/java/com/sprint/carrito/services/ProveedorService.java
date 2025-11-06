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

    @Override
    protected void validar(Proveedor prov) throws ErrorServiceException {
        try {
            if (prov.getNombre() == null || prov.getNombre().trim().isEmpty()) {
                throw new ErrorServiceException("El nombre no puede estar vacío");
            }

            if (prov.getDireccion() == null || prov.getDireccion().trim().isEmpty()) {
                throw new ErrorServiceException("La dirección no puede estar vacía");
            }

            if (prov.getLatitud() == null || prov.getLongitud() == null) {
                throw new ErrorServiceException("Debe seleccionar una ubicación en el mapa");
            }

            if (prov.getLatitud() < -90 || prov.getLatitud() > 90) {
                throw new ErrorServiceException("La latitud debe estar entre -90 y 90");
            }

            if (prov.getLongitud() < -180 || prov.getLongitud() > 180) {
                throw new ErrorServiceException("La longitud debe estar entre -180 y 180");
            }

        } catch(ErrorServiceException e) {
            throw e;
        } catch(Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}
