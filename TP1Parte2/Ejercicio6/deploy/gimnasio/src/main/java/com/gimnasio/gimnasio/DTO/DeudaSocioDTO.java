package com.gimnasio.gimnasio.DTO;

import com.gimnasio.gimnasio.entities.Socio;
import lombok.Getter;

@Getter
public class DeudaSocioDTO {

    private String socioId;
    private Long numeroSocio;
    private String nombreCompleto;
    private int mesesDeuda;
    private double totalDeuda;

    public DeudaSocioDTO(Socio socio, int mesesDeuda, double totalDeuda) {
        this.socioId = socio.getId();
        this.numeroSocio = socio.getNumeroSocio();
        this.nombreCompleto = socio.getApellido() + ", " + socio.getNombre();
        this.mesesDeuda = mesesDeuda;
        this.totalDeuda = totalDeuda;
    }

}
