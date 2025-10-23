package com.sprint.videojuegos.services;
import com.sprint.videojuegos.entities.Categoria;
import com.sprint.videojuegos.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService extends BaseService<Categoria, Long> {
    @Autowired
    private CategoriaRepository repositorio;

    public CategoriaService(CategoriaRepository repositorio) {
        super(repositorio);
    }


}