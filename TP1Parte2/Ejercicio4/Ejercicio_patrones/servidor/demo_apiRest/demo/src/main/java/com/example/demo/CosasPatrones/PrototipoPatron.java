package com.example.demo.CosasPatrones;

public class PrototipoPatron implements Cloneable {

    // Implementación del patrón Prototipo para clonar objetos de autor

    private String nombre;
    private String apellido;
    private String biografia;

    public PrototipoPatron(String nombreAutor, String apellidoAutor, String biografiaAutor) {
        this.nombre = nombreAutor;
        this.apellido = apellidoAutor;
        this.biografia = biografiaAutor;
    }

    @Override
    public PrototipoPatron clone() {
        try {
            return (PrototipoPatron) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}