package com.example.demo.CosasPatrones;

import com.example.demo.entities.Libro;

public class EstrategiaPatron {

// Implementación del patrón Estrategia para buscar libros por título, género y autor

    public interface EstrategiaBusqueda {
        boolean cumple(Libro libro, String criterio);
    }

    public class BuscarPorTitulo implements EstrategiaBusqueda {
        @Override
        public boolean cumple(Libro libro, String criterio) {
            return libro.getTitulo().equalsIgnoreCase(criterio);
        }
    }

    public class BuscarPorGenero implements EstrategiaBusqueda {
        @Override
        public boolean cumple(Libro libro, String criterio) {
            return libro.getGenero().equalsIgnoreCase(criterio);
        }
    }

    public class BuscarPorAutor implements EstrategiaBusqueda {
        @Override
        public boolean cumple(Libro libro, String criterio) {
            return libro.getAutor().equalsIgnoreCase(criterio);
        }
    }

}
