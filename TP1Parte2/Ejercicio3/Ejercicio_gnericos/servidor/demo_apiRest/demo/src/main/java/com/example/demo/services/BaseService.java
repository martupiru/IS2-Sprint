package com.example.demo.services;

import com.example.demo.entities.BaseEntity;
import com.example.demo.repositories.BaseRepository;

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
            throw new ErrorServiceException("Error de Sistemas");
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
            throw new ErrorServiceException("Error de Sistemas");
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
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public Optional<T> obtener(ID id)throws ErrorServiceException {
        try {

            return repository.findById(id).
                    filter(e -> !Boolean.TRUE.equals(e.getEliminado()));

        }catch(Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public List<T> listarActivos()throws ErrorServiceException {
        try {

            return repository.findAll().stream()
                    .filter(e -> !Boolean.TRUE.equals(e.getEliminado()))
                    .toList();

        }catch(Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public T getOne(ID id) throws ErrorServiceException {
        try {
            Optional<T> entidad = repository.findById(id);
            if (entidad.isPresent() && !Boolean.TRUE.equals(entidad.get().getEliminado())) {
                return entidad.get();
            } else {
                throw new ErrorServiceException("Entidad no encontrada o eliminada");
            }
        } catch (Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    //Metodos para ser redefinidos en las clases de servicio que heredan, con el objetivo
    //que sea necesario realizar acciones previas o posteriores en las Altas, Bajas y
    //Modificaciones.
    //Se deberá redefinir el comportamiento en la clase que hereda.
    protected void validar(T entidad) throws ErrorServiceException {}
    protected void preAlta(T entidad) throws ErrorServiceException {}
    protected void postAlta(T entidad)throws ErrorServiceException {}
    protected void preModificacion(T entidad)throws ErrorServiceException {}
    protected void preBaja(ID id)throws ErrorServiceException {}
}