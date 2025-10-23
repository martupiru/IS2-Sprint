package com.sprint.videojuegos.services;

import com.sprint.videojuegos.entities.Estudio;
import com.sprint.videojuegos.repositories.EstudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudioService extends BaseService<Estudio, Long> {
    @Autowired
    private EstudioRepository repositorio;

    public EstudioService(EstudioRepository repositorio) {
        super(repositorio);
    }
}
