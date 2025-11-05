package com.sprint.ejercicio2b.services;

import com.sprint.ejercicio2b.entities.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LibroService extends BaseService<Libro, Long> {
    List<Libro> searchByTitulo(String titulo) throws Exception;
    Page<Libro> searchByTitulo(String titulo, Pageable pageable) throws Exception;
    List<Libro> searchByGenero(String genero) throws Exception;
    Page<Libro> searchByGenero(String genero, Pageable pageable) throws Exception;
    List<Libro> searchByAutor(String nombreAutor) throws Exception;
    Page<Libro> searchByAutor(String nombreAutor, Pageable pageable) throws Exception;
}
