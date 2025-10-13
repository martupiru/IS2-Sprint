package com.example.demo.services;

import com.example.demo.entities.Libro;
import com.example.demo.repositories.LibroRepository;
import org.springframework.stereotype.Service;

@Service
public class LibroService extends BaseService<Libro, Long> {

    public LibroService(LibroRepository repository) {
        super(repository);
    }

    protected void validar(Libro libro) throws ErrorServiceException {
        try {
            if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
                throw new ErrorServiceException("Debe indicar el título del libro");
            }
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}