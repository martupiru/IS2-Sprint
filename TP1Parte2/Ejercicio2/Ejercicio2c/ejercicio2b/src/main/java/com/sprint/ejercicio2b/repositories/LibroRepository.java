package com.sprint.ejercicio2b.repositories;

import com.sprint.ejercicio2b.entities.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends BaseRepository<Libro, Long> {

    // Búsqueda por título
    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo%")
    List<Libro> searchByTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo%")
    Page<Libro> searchByTitulo(@Param("titulo") String titulo, Pageable pageable);

    // Búsqueda por género
    @Query("SELECT l FROM Libro l WHERE l.genero LIKE %:genero%")
    List<Libro> searchByGenero(@Param("genero") String genero);

    @Query("SELECT l FROM Libro l WHERE l.genero LIKE %:genero%")
    Page<Libro> searchByGenero(@Param("genero") String genero, Pageable pageable);

    // Búsqueda por autor
    @Query("SELECT l FROM Libro l JOIN l.autores a WHERE a.nombre LIKE %:nombreAutor% OR a.apellido LIKE %:nombreAutor%")
    List<Libro> searchByAutor(@Param("nombreAutor") String nombreAutor);

    @Query("SELECT l FROM Libro l JOIN l.autores a WHERE a.nombre LIKE %:nombreAutor% OR a.apellido LIKE %:nombreAutor%")
    Page<Libro> searchByAutor(@Param("nombreAutor") String nombreAutor, Pageable pageable);
}
