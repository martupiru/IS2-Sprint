package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutorDTO implements Identifiable<Long> {
    private Long id;             // necesario para el controlador genérico
    private String nombre;
    private String apellido;
    private String biografia;
}