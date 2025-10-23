package com.gimnasio.gimnasio.services;


import java.util.List;
/*  E --> generico */
public interface ServicioBase <E>{
    List<E> findAll() throws  Exception;
    E findById(String id) throws Exception;
    boolean deleteById(String id) throws Exception;
}