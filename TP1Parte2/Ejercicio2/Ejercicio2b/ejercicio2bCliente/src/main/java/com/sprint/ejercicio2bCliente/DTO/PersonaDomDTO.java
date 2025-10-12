package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDomDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private int dni;
    private DomicilioDTO domicilio;
}
