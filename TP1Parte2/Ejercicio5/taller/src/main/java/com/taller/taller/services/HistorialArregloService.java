package com.taller.taller.services;

import com.taller.taller.entities.HistorialArreglo;
import com.taller.taller.repositories.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class HistorialArregloService extends BaseService<HistorialArreglo, String> {

    public HistorialArregloService(BaseRepository<HistorialArreglo, String> repository) {
        super(repository);
    }

    @Override
    protected void validar(HistorialArreglo entidad) throws ErrorServiceException {
        if (entidad == null) throw new ErrorServiceException("Historial nulo");
        if (entidad.getDetalleArreglo() == null || entidad.getDetalleArreglo().isBlank())
            throw new ErrorServiceException("Detalle del arreglo es obligatorio");
    }
}

