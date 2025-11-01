package com.is.biblioteca.patrones.Strategy;

import com.is.biblioteca.business.domain.entity.Libro;
import com.is.biblioteca.business.logic.error.ErrorServiceException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibroBusquedaContext {

    private LibroBusquedaStrategy estrategia;

    public void setEstrategia(LibroBusquedaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public List<Libro> ejecutarBusqueda(String valor) throws ErrorServiceException {
        if (estrategia == null) {
            throw new ErrorServiceException("No se ha definido una estrategia de búsqueda");
        }
        return estrategia.buscar(valor);
    }
}
