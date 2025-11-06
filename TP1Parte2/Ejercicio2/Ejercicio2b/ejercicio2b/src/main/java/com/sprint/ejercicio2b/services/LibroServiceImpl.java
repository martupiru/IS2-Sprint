package com.sprint.ejercicio2b.services;

import com.sprint.ejercicio2b.entities.Libro;
import com.sprint.ejercicio2b.repositories.BaseRepository;
import com.sprint.ejercicio2b.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroServiceImpl extends BaseServiceImpl<Libro, Long> implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public LibroServiceImpl(BaseRepository<Libro, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> searchByTitulo(String titulo) throws Exception {
        try {
            return libroRepository.searchByTitulo(titulo);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Libro> searchByTitulo(String titulo, Pageable pageable) throws Exception {
        try {
            return libroRepository.searchByTitulo(titulo, pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> searchByGenero(String genero) throws Exception {
        try {
            return libroRepository.searchByGenero(genero);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Libro> searchByGenero(String genero, Pageable pageable) throws Exception {
        try {
            return libroRepository.searchByGenero(genero, pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> searchByAutor(String nombreAutor) throws Exception {
        try {
            return libroRepository.searchByAutor(nombreAutor);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Libro> searchByAutor(String nombreAutor, Pageable pageable) throws Exception {
        try {
            return libroRepository.searchByAutor(nombreAutor, pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}