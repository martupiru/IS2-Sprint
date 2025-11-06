package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DomicilioDTO {
    private String calle;
    private int numero;
    private LocalidadDTO localidad;

}
