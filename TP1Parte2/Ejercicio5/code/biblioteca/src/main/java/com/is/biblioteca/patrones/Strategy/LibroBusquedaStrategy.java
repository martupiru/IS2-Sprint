package com.is.biblioteca.patrones.Strategy;

import com.is.biblioteca.business.domain.entity.Libro;
import com.is.biblioteca.business.logic.error.ErrorServiceException;

import java.util.List;

public interface LibroBusquedaStrategy {
    List<Libro> buscar(String valor) throws ErrorServiceException;
}
