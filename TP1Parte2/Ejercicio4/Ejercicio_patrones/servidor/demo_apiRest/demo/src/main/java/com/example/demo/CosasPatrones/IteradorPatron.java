package com.example.demo.CosasPatrones;

import com.example.demo.entities.Libro;

public class IteradorPatron {

    // Iterador: Utilizar el patrón para recorrer una lista de libros y obtener los que son de un determinado autor

    public interface IteradorLibro {
        boolean tieneSiguiente();
        Libro siguiente();
    }

    public static class ColeccionLibros {
        private java.util.List<Libro> libros = new java.util.ArrayList<>();

        public void agregarLibro(Libro libro) {
            libros.add(libro);
        }

        public IteradorLibro iteradorPorAutor(String autor) {
            return new IteradorPorAutor(autor);
        }

        private class IteradorPorAutor implements IteradorLibro {
            private String autor;
            private int posicion = 0;

            public IteradorPorAutor(String autor) {
                this.autor = autor;
            }

            @Override
            public boolean tieneSiguiente() {
                while (posicion < libros.size()) {
                    if (libros.get(posicion).getAutor().equals(autor)) {
                        return true;
                    }
                    posicion++;
                }
                return false;
            }

            @Override
            public Libro siguiente() {
                return libros.get(posicion++);
            }
        }
    }
}
