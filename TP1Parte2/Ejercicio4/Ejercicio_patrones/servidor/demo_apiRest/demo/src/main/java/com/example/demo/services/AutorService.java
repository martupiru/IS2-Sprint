package com.example.demo.services;

import com.example.demo.entities.Autor;
import com.example.demo.repositories.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService extends BaseService<Autor, Long> {

    public AutorService(AutorRepository repository) {
        super(repository);
    }

    protected void validar(Autor autor) throws ErrorServiceException {
        try {
            if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
                throw new ErrorServiceException("Debe indicar el nombre del autor");
            }
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}