package com.sprint.part2ej1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "proveedores")
public class Proveedor extends Persona{

    @NotEmpty(message = "El CUIT no puede estar vacío")
    @Size(max = 11, message = "El CUIT no puede superar los 11 caracteres")
    @Column(name = "cuit", unique = true,nullable = false)
    private String cuit;

    // Relación 1.. con Direccion
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_proveedor")
    private List<Direccion> direcciones;

}
