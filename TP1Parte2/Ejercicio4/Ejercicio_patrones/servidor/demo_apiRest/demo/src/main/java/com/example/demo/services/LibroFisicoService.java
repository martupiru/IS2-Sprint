package com.example.demo.services;

import com.example.demo.entities.Libro;
import com.example.demo.entities.LibroDigital;
import com.example.demo.entities.LibroFisico;
import com.example.demo.repositories.LibroDigitalRepository;
import com.example.demo.repositories.LibroFisicoRepository;
import com.example.demo.repositories.LibroRepository;
import org.springframework.stereotype.Service;

@Service
public class LibroFisicoService extends BaseService<LibroFisico, Long> {

    public LibroFisicoService(LibroFisicoRepository repository) {
        super(repository);
    }
    protected void validar(LibroFisico libro) throws ErrorServiceException {
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