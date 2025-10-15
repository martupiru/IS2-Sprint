package com.sprint.KioscoPatrones.services;

import java.util.List;

public interface BaseService<E, ID> {
    List<E> findAllActivos() throws Exception;
    E findById(ID id) throws Exception;
    E save(E entity) throws Exception;
    E update(ID id, E entity) throws Exception;
    boolean deleteById(ID id) throws Exception;
}
