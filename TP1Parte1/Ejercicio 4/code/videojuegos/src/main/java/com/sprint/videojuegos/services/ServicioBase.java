package com.sprint.videojuegos.services;

import java.util.List;
/*  E --> generico */
public interface ServicioBase <E>{
    List<E> findAll() throws  Exception;
    E findById(long id) throws Exception;
    E saveOne(E entity) throws Exception;
    E updateOne(E entity, long id) throws Exception;
    boolean deleteById(long id) throws Exception;
}
