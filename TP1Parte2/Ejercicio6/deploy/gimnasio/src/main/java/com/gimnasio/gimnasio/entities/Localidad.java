package com.gimnasio.gimnasio.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "localidades")
public class Localidad {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotEmpty(message = "El código postal no puede estar vacío")
    @Size(max = 10, message = "El código postal no puede superar los 10 caracteres")
    private String codigoPostal;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private Boolean eliminado = false;

    @NotNull(message = "El campo es requerido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_departamento", nullable = false)
    private Departamento departamento;

}
