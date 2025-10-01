package com.sprint.part2ej1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class Usuario extends Persona {


    @NotEmpty(message = "La sección cuenta no puede estar vacío")
    @Size(max = 50, message = "La sección cuenta no puede superar los 50 caracteres")
    @Column(name = "cuentas")
    private String cuenta;

    @NotEmpty(message = "La clave no puede estar vacía")
    private String clave;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private boolean eliminado;

}
