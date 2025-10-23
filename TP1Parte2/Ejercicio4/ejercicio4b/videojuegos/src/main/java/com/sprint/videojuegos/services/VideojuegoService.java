package com.sprint.videojuegos.services;

import com.sprint.videojuegos.entities.Videojuego;
import com.sprint.videojuegos.repositories.VideojuegoRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class VideojuegoService extends BaseService<Videojuego, Long>{
    @Autowired
    private VideojuegoRepository repositorio;

    public VideojuegoService(VideojuegoRepository repositorio) {
        super(repositorio);
    }

    @Transactional
    public List<Videojuego> findByTitle(String q) throws Exception{
        try{
            List<Videojuego> entities = repositorio.findByTitle(q);
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


}