package com.is.biblioteca.patrones.Strategy;

import com.is.biblioteca.business.domain.entity.Libro;
import com.is.biblioteca.business.logic.error.ErrorServiceException;
import com.is.biblioteca.business.logic.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusquedaPorEditorial implements LibroBusquedaStrategy {

    @Autowired
    private LibroService libroService;

    @Override
    public List<Libro> buscar(String nombreEditorial) throws ErrorServiceException {
        return libroService.listarLibroPorEditorial(nombreEditorial);
    }
}