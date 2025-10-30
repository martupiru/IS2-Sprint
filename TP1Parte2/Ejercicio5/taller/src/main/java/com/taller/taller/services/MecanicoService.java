package com.taller.taller.services;

import com.taller.taller.entities.Mecanico;
import com.taller.taller.repositories.MecanicoRepository;
import org.springframework.stereotype.Service;

@Service
public class MecanicoService extends BaseService<Mecanico, String> {

    private final MecanicoRepository mecanicoRepository;

    public MecanicoService(MecanicoRepository repository) {
        super(repository);
        this.mecanicoRepository = repository;
    }

    @Override
    protected Mecanico createEmpty() {
        Mecanico m = new Mecanico();
        m.setEliminado(false);
        return m;
    }

    @Override
    protected void validar(Mecanico entidad) throws ErrorServiceException {
        if (entidad == null) throw new ErrorServiceException("Mecánico nulo");
        if (entidad.getLegajo() == null || entidad.getLegajo().isBlank())
            throw new ErrorServiceException("Legajo es obligatorio");
    }
}

