package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.Base;
import com.sprint.consultorio.repositories.BaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<E extends Base, ID extends Serializable> implements BaseService<E, ID> {

    @Autowired
    protected BaseRepository<E, ID> baseRepository;

    @Override
    @Transactional
    public List<E> findAll() throws Exception {
        try {
            return baseRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            return entityOptional.orElseThrow(() -> new Exception("No se encontró la entidad con el id proporcionado."));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            beforeSave(entity);
            entity = baseRepository.save(entity);
            afterSave(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception("Error al guardar la entidad: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            if (baseRepository.findById(id).isEmpty()) {
                throw new Exception("Entidad no encontrada para actualizar.");
            }
            beforeUpdate(entity);
            E entityUpdate = baseRepository.save(entity);
            afterUpdate(entityUpdate);
            return entityUpdate;
        } catch (Exception e) {
            throw new Exception("Error al actualizar la entidad: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            if (baseRepository.existsById(id)) {
                E entity = findById(id);
                entity.setEliminado(true);
                baseRepository.save(entity);
                return true;
            } else {
                throw new Exception("No se encontró la entidad a eliminar.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    protected void beforeSave(E entity) throws Exception {}
    protected void afterSave(E entity) throws Exception {}
    protected void beforeUpdate(E entity) throws Exception {}
    protected void afterUpdate(E entity) throws Exception {}
}
