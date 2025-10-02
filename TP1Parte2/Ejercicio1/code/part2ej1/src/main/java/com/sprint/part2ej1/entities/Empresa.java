package com.sprint.part2ej1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

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

    // Relación 1..* con Direccion
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_proveedor")
    private List<Direccion> direcciones;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private Boolean eliminado = false;
}