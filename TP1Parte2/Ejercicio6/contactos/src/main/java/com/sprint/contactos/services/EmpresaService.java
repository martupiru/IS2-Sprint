package com.sprint.contactos.services;

import com.sprint.contactos.entities.Empresa;
import com.sprint.contactos.repositories.EmpresaRepository;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService extends BaseService<Empresa, String> {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository repository) {
        super(repository);
        this.empresaRepository = repository;
    }

    @Override
    protected Empresa createEmpty() {
        Empresa empresa = new Empresa();
        empresa.setEliminado(false);
        return empresa;
    }

    @Override
    protected void validar(Empresa entidad) throws ErrorServiceException {
        if (entidad.getNombre() == null || entidad.getNombre().isBlank()) {
            throw new ErrorServiceException("El nombre de la empresa es obligatorio.");
        }

        // Solo letras y espacios
        if (!entidad.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            throw new ErrorServiceException("El nombre de la empresa solo puede contener letras y espacios.");
        }

        // Evitar duplicados (case insensitive)
        if (empresaRepository.findByNombreIgnoreCase(entidad.getNombre())
                .filter(e -> !e.getId().equals(entidad.getId()))
                .isPresent()) {
            throw new ErrorServiceException("Ya existe una empresa con ese nombre.");
        }

    }
}
