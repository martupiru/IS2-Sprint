package com.sprint.KioscoPatrones.services;

import com.sprint.KioscoPatrones.entities.Persona;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public abstract class BaseServiceImpl<E extends Persona, ID> implements BaseService<E, ID> {

    protected abstract JpaRepository<E, ID> getJpaRepository();

    @Override
    @Transactional
    public List<E> findAllActivos() throws Exception {
        try {
            return getJpaRepository().findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityOptional = getJpaRepository().findById(id);
            if (entityOptional.isEmpty() || entityOptional.get().isEliminado()) {
                throw new Exception("Entidad no encontrada o eliminada.");
            }
            return entityOptional.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            return getJpaRepository().save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            if (getJpaRepository().existsById(id)) {
                return getJpaRepository().save(entity);
            }
            throw new Exception("Entidad no encontrada para actualizar.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(ID id) throws Exception {
        try {
            Optional<E> entityOptional = getJpaRepository().findById(id);
            if (entityOptional.isPresent()) {
                E entity = entityOptional.get();
                entity.setEliminado(true); // Soft delete
                getJpaRepository().save(entity);
                return true;
            }
            throw new Exception("Entidad no encontrada para eliminar.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}


