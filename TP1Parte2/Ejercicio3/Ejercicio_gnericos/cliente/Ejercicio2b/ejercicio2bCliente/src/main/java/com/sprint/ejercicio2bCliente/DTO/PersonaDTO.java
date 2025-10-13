package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonaDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private int dni;
    private DomicilioDTO domicilio;
    private List<LibroDTO> libros = new java.util.ArrayList<LibroDTO>();
}
