package com.sprint.contactos.services;

import com.sprint.contactos.entities.ContactoCorreoElectronico;
import com.sprint.contactos.repositories.ContactoCorreoElectronicoRepository;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class ContactoCorreoElectronicoService extends BaseService<ContactoCorreoElectronico, String> {

    private final ContactoCorreoElectronicoRepository correoRepository;

    public ContactoCorreoElectronicoService(ContactoCorreoElectronicoRepository repository) {
        super(repository);
        this.correoRepository = repository;
    }

    @Override
    protected ContactoCorreoElectronico createEmpty() {
        ContactoCorreoElectronico c = new ContactoCorreoElectronico();
        c.setEliminado(false);
        return c;
    }

    @Override
    protected void validar(ContactoCorreoElectronico entidad) throws ErrorServiceException {
        if (entidad.getEmail() == null || entidad.getEmail().isBlank()) {
            throw new ErrorServiceException("El correo electrónico es obligatorio.");
        }

        // Validar formato de email con regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!Pattern.matches(emailRegex, entidad.getEmail())) {
            throw new ErrorServiceException("El formato del correo electrónico no es válido.");
        }

        // Evitar correos duplicados (ignorando mayúsculas)
        var existente = correoRepository.findByEmailIgnoreCase(entidad.getEmail());
        if (existente.isPresent()) {
            var c = existente.get();
            if (entidad.getId() == null || !c.getId().equals(entidad.getId())) {
                throw new ErrorServiceException("Ya existe un contacto con ese correo electrónico.");
            }
        }


    }
}
