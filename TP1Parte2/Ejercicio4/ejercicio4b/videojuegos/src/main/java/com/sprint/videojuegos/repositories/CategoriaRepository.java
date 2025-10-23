package com.sprint.videojuegos.repositories;

import com.sprint.videojuegos.entities.Categoria;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long> {
}
