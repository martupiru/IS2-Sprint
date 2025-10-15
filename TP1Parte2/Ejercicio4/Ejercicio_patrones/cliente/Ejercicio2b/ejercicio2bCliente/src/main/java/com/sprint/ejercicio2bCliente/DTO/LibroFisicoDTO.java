package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibroFisicoDTO extends LibroDTO {
    private String ubicacionEstante;

    public LibroFisicoDTO() {
        this.tipoLibro = "FISICO";
    }
}
