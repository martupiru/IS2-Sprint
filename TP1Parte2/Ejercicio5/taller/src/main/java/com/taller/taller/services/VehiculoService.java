package com.taller.taller.services;

import com.taller.taller.entities.Vehiculo;
import com.taller.taller.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService extends BaseService<Vehiculo, String> {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository repository) {
        super(repository);
        this.vehiculoRepository = repository;
    }

    @Override
    protected Vehiculo createEmpty() {
        Vehiculo v = new Vehiculo();
        v.setEliminado(false);
        return v;
    }

    @Override
    protected void validar(Vehiculo entidad) throws ErrorServiceException {
        if (entidad == null) throw new ErrorServiceException("Vehiculo nulo");
        if (entidad.getPatente() == null || entidad.getPatente().isBlank())
            throw new ErrorServiceException("Patente es obligatoria");
    }
}

