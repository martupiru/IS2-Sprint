package com.sprint.contactos.services;

import com.sprint.contactos.entities.Persona;
import com.sprint.contactos.repositories.ContactoCorreoElectronicoRepository;
import com.sprint.contactos.repositories.ContactoTelefonicoRepository;
import com.sprint.contactos.repositories.PersonaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService extends BaseService<Persona, String> {

    private final ContactoCorreoElectronicoRepository correoRepo;
    private final ContactoTelefonicoRepository telefonoRepo;

    public PersonaService(PersonaRepository repository, ContactoCorreoElectronicoRepository correoRepo,
                          ContactoTelefonicoRepository telefonoRepo) {
        super(repository);
        this.correoRepo = correoRepo;
        this.telefonoRepo = telefonoRepo;
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

    @Override
    protected void preBaja(String id) throws ErrorServiceException {
        try {
            // Marcar los correos asociados como eliminados
            correoRepo.findAll().stream()
                    .filter(c -> c.getPersona() != null && id.equals(c.getPersona().getId()))
                    .forEach(c -> {
                        c.setEliminado(true);
                        correoRepo.save(c);
                    });

            // Marcar los teléfonos asociados como eliminados
            telefonoRepo.findAll().stream()
                    .filter(t -> t.getPersona() != null && id.equals(t.getPersona().getId()))
                    .forEach(t -> {
                        t.setEliminado(true);
                        telefonoRepo.save(t);
                    });

        } catch (Exception e) {
            throw new ErrorServiceException("Error al eliminar los contactos de la persona: " + e.getMessage());
        }
    }
}

