package com.sprint.part2ej1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "proveedores")
public class Proveedor {
    @Id
    @NotEmpty(message = "El CUIT no puede estar vacío")
    @Size(max = 11, message = "El CUIT no puede superar los 11 caracteres")
    @Column(name = "cuit")
    private String cuit;
}
