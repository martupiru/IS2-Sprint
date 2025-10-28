package com.gimnasio.gimnasio.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "socios")
public class Socio extends Persona {

    @NotNull(message = "El número de socio es obligatorio")
    @Min(value = 1, message = "El número de socio debe ser mayor a 0")
    @Column(name = "numero_socio", unique = true, nullable = false) // Para que no se repita
    private Long numeroSocio;

}
