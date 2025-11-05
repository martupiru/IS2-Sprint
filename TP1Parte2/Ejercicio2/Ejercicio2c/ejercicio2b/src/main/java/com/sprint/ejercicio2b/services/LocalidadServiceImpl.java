package com.sprint.ejercicio2b.services;

import com.sprint.ejercicio2b.entities.Localidad;
import com.sprint.ejercicio2b.repositories.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class LocalidadServiceImpl extends BaseServiceImpl<Localidad, Long> implements LocalidadService {
    public LocalidadServiceImpl(BaseRepository<Localidad, Long> baseRepository) {
        super(baseRepository);
    }
}
