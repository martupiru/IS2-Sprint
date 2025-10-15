package com.example.demo.services;

import com.example.demo.entities.Persona;
import com.example.demo.repositories.PersonaRepository;
import org.springframework.stereotype.Service;



@Service
public class PersonaService extends BaseService<Persona, Long> {

    public PersonaService(PersonaRepository repository) {
        super(repository);
    }

    protected void validar(Persona persona) throws ErrorServiceException {
        try {
            if (persona.getNombre() == null || persona.getNombre().trim().isEmpty()) {
                throw new ErrorServiceException("Debe indicar el nombre de la persona");
            }
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}