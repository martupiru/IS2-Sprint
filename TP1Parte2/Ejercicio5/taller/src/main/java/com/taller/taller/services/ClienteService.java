package com.taller.taller.services;

import com.taller.taller.entities.Cliente;
import com.taller.taller.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends BaseService<Cliente, String> {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository repository) {
        super(repository);
        this.clienteRepository = repository;
    }

    @Override
    protected Cliente createEmpty() {
        Cliente c = new Cliente();
        c.setEliminado(false); // default coherente
        return c;
    }

    @Override
    protected void validar(Cliente entidad) throws ErrorServiceException {
        if (entidad == null) throw new ErrorServiceException("Cliente nulo");
        if (entidad.getDocumento() == null || entidad.getDocumento().isBlank())
            throw new ErrorServiceException("Documento del cliente es obligatorio");
    }
}

