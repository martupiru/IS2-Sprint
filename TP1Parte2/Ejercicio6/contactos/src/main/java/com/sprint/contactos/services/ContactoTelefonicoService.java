package com.sprint.contactos.services;

import com.sprint.contactos.entities.ContactoTelefonico;
import com.sprint.contactos.repositories.ContactoTelefonicoRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactoTelefonicoService extends BaseService<ContactoTelefonico, String> {

    private final ContactoTelefonicoRepository telefonoRepository;

    public ContactoTelefonicoService(ContactoTelefonicoRepository repository) {
        super(repository);
        this.telefonoRepository = repository;
    }

    @Override
    protected ContactoTelefonico createEmpty() {
        ContactoTelefonico c = new ContactoTelefonico();
        c.setEliminado(false);
        return c;
    }

    @Override
    protected void validar(ContactoTelefonico entidad) throws ErrorServiceException {
        if (entidad.getTelefono() == null || entidad.getTelefono().isBlank()) {
            throw new ErrorServiceException("El número de teléfono es obligatorio.");
        }

        // Validar formato numérico (+, espacios o guiones permitidos)
        if (!entidad.getTelefono().matches("^[+0-9\\s-]+$")) {
            throw new ErrorServiceException("El número de teléfono solo puede contener números, espacios o '+'.");
        }

        // Evitar duplicados
        var existente = telefonoRepository.findByTelefono(entidad.getTelefono());
        if (existente.isPresent() && !existente.get().getId().equals(entidad.getId())) {
            throw new ErrorServiceException("Ya existe un contacto con ese número de teléfono.");
        }
    }
}
