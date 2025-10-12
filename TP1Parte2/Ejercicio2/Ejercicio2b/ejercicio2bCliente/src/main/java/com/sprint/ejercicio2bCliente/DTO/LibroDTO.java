package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LibroDTO {
    private String titulo;
    private int fecha;
    private String genero;
    private int paginas;
    private List<AutorDTO> autores;
}
