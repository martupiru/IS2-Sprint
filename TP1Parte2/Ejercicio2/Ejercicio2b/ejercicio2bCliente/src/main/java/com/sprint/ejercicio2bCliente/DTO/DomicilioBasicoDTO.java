package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DomicilioBasicoDTO {
    private Long id;
    private String calle;
    private int numero;
    private String localidadDenominacion; // para mostrar el nombre de la localidad
}
