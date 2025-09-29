package com.sprint.part2ej1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empresas")
public class Empresa{

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;


    @NotEmpty(message = "La razon social no puede estar vacío")
    @Size(max = 50, message = "La razon social no puede superar los 50 caracteres")
    private String razonSocial;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private Boolean eliminado = false;
}