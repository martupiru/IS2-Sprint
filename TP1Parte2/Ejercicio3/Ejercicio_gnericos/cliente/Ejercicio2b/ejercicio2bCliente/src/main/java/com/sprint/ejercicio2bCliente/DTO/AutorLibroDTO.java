package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AutorLibroDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String biografia;
    private List<LibroDTO> libros = new java.util.ArrayList<LibroDTO>();

}
