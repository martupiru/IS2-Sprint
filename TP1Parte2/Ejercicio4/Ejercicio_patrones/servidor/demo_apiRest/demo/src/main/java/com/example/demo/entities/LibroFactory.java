package com.example.demo.entities;

public class LibroFactory {

    public enum TipoLibro {
        FISICO,
        DIGITAL
    }

    public static Libro crearLibro(TipoLibro tipo) {
        switch (tipo) {
            case FISICO:
                return new LibroFisico();
            case DIGITAL:
                return new LibroDigital();
            default:
                throw new IllegalArgumentException("Tipo de libro no válido: " + tipo);
        }
    }

    public static LibroFisico crearLibroFisico(String ubicacionEstante) {
        LibroFisico libro = new LibroFisico();
        libro.setUbicacionEstante(ubicacionEstante);
        return libro;
    }


    public static LibroDigital crearLibroDigital(String urlDescarga) {
        LibroDigital libro = new LibroDigital();
        libro.setUrlDescarga(urlDescarga);
        return libro;
    }
}
