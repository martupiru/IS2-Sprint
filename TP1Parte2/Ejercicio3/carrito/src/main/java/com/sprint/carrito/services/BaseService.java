package com.sprint.carrito.services;

import com.sprint.carrito.entities.BaseEntity;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.repositories.BaseRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity<ID>, ID> {

    protected final BaseRepository<T, ID> repository;

    protected BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T alta(T entidad) throws ErrorServiceException {
        try {
            validar(entidad);
            preAlta(entidad);
            entidad.setEliminado(false);
            T guardado = repository.save(entidad);
            postAlta(guardado);
            return guardado;
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException(e.getMessage());
        }
    }

    public Optional<T> modificar(ID id, T entidadNueva)throws ErrorServiceException {
        try {
            validar(entidadNueva);
            preModificacion(entidadNueva);
            return repository.findById(id).map(entidad -> {
                entidadNueva.setId(id);
                T actualizado = repository.save(entidadNueva);
                return actualizado;
            });

        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException(e.getMessage());
        }
    }

    public boolean bajaLogica(ID id)throws ErrorServiceException {
        try {
            preBaja(id);
            return repository.findById(id).map(entidad -> {
                entidad.setEliminado(true);
                repository.save(entidad);
                return true;
            }).orElse(false);
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException(e.getMessage());
        }
    }

    public Optional<T> obtener(ID id)throws ErrorServiceException {
        try {
            return repository.findById(id).
                    filter(e -> !Boolean.TRUE.equals(e.getEliminado()));
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException(e.getMessage());
        }
    }

    public List<T> listarActivos()throws ErrorServiceException {
        try {
            return repository.findAll().stream()
                    .filter(e -> !Boolean.TRUE.equals(e.getEliminado()))
                    .toList();
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException(e.getMessage());
        }
    }

    protected void validar(T entidad) throws ErrorServiceException {}
    protected void preAlta(T entidad) throws ErrorServiceException {}
    protected void postAlta(T entidad)throws ErrorServiceException {}
    protected void preModificacion(T entidad)throws ErrorServiceException {}
    protected void preBaja(ID id)throws ErrorServiceException {}
}