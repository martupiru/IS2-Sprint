package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibroBasicoDTO {
    private Long id;
    private String titulo;
    private String genero;
    private int fecha;
}