package com.gimnasio.gimnasio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "promociones")
public class Promocion extends Mensaje{
    private LocalDate fechaEnvioPromocion;
    @Min(value = 0, message = "La cantidad de socios enviados no puede ser negativa")
    private long cantidadSociosEnviados;
}
