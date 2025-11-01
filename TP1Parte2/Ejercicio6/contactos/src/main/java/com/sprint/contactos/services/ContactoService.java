package com.sprint.contactos.services;

import com.sprint.contactos.entities.Contacto;
import com.sprint.contactos.repositories.ContactoRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactoService extends BaseService<Contacto, String> {

    public ContactoService(ContactoRepository repository) {
        super(repository);
    }

    @Override
    protected Contacto createEmpty() {
        return null; // No se crea directamente, se usan las subclases
    }

    @Override
    protected void validar(Contacto entidad) throws ErrorServiceException {
        if (entidad.getTipoContacto() == null) {
            throw new ErrorServiceException("Debe especificar el tipo de contacto (PERSONAL o LABORAL)");
        }
    }
}
