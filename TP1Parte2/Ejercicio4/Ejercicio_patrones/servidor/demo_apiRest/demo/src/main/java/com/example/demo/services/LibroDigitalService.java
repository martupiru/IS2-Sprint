package com.example.demo.services;

import com.example.demo.entities.LibroDigital;
import com.example.demo.entities.LibroFisico;
import com.example.demo.repositories.LibroDigitalRepository;
import com.example.demo.repositories.LibroRepository;
import org.springframework.stereotype.Service;

@Service
public class LibroDigitalService extends BaseService<LibroDigital, Long> {

    public LibroDigitalService(LibroDigitalRepository repository) {
        super(repository);
    }
    protected void validar(LibroDigital libro) throws ErrorServiceException {
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
