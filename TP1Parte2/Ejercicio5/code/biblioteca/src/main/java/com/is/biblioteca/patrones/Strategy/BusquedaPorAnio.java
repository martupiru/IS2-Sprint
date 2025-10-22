package com.is.biblioteca.patrones.Strategy;

import com.is.biblioteca.business.domain.entity.Libro;
import com.is.biblioteca.business.logic.error.ErrorServiceException;
import com.is.biblioteca.business.logic.service.LibroService;
import com.is.biblioteca.business.persistence.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusquedaPorAnio implements LibroBusquedaStrategy {

    @Autowired
    private LibroService libroService;

    @Override
    public List<Libro> buscar(String valor) throws ErrorServiceException {
        Integer anio;
        try {
            anio = Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new ErrorServiceException("El año debe ser un número válido");
        }

        return libroService.listarLibroPorAnio(anio);
    }
}