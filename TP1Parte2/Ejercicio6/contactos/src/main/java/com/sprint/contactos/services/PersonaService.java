package com.sprint.contactos.services;

import com.sprint.contactos.entities.Persona;
import com.sprint.contactos.repositories.PersonaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService extends BaseService<Persona, String> {

    public PersonaService(PersonaRepository repository) {
        super(repository);
    }

    @Override
    protected Persona createEmpty() {
        Persona persona = new Persona();
        persona.setEliminado(false);
        return persona;
    }

    @Override
    protected void validar(Persona entidad) throws ErrorServiceException {
        if (entidad.getNombre() == null || entidad.getNombre().isBlank()) {
            throw new ErrorServiceException("El nombre de la persona es obligatorio");
        }
        if (entidad.getApellido() == null || entidad.getApellido().isBlank()) {
            throw new ErrorServiceException("El apellido es obligatorio");
        }
    }
}

