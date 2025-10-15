package com.example.demo.CosasPatrones;

import com.example.demo.entities.Libro;

public class BuilderPatron {

    // Builder: Utilizar el patrón para crear objetos del tipo Libro.

    public static class BuilderLibro {
        private String titulo;
        private String autor;
        private int paginas;

        public BuilderLibro setTitulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public BuilderLibro setAutor(String autor) {
            this.autor = autor;
            return this;
        }

        public BuilderLibro setPaginas(int paginas) {
            this.paginas = paginas;
            return this;
        }

        public Libro build() {
            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setPaginas(paginas);
            return libro;
        }

    }

}
