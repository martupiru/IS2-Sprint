package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibroDigitalDTO extends LibroDTO {
    private String urlDescarga;

    public LibroDigitalDTO() {
        this.tipoLibro = "DIGITAL";
    }
}
