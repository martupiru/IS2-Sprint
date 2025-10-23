package com.sprint.videojuegos.repositories;

import com.sprint.videojuegos.entities.Videojuego;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideojuegoRepository extends BaseRepository<Videojuego, Long> {
    @Query(value= "SELECT * FROM videojuegos WHERE videojuegos.eliminado = false", nativeQuery = true)
    List<Videojuego> findAllAndEliminadoFalse();

    @Query(value = "SELECT * FROM videojuegos  WHERE videojuegos.id = :id AND videojuegos.eliminado = false", nativeQuery = true)
    Optional<Videojuego> findByIdAndEliminadoFalse(@Param("id") long id);

    @Query(value = "SELECT * FROM videojuegos WHERE videojuegos.titulo LIKE %:q% AND videojuegos.eliminado =false", nativeQuery = true)
    List<Videojuego> findByTitle(@Param("q")String q);
}

