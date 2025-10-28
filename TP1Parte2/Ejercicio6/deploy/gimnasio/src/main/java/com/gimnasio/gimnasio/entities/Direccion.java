package com.gimnasio.gimnasio.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "direcciones")
public class Direccion {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String calle;

    @NotEmpty(message = "La numeración no puede estar vacía")
    @Size(max = 10, message = "La numeración no puede superar los 10 caracteres")
    private String numeracion;

    @Size(max = 50, message = "El barrio no puede superar los 50 caracteres")
    @NotEmpty(message = "El barrio no puede estar vacío")
    private String barrio;

    @Size(max = 10, message = "La manzana/piso no puede superar los 10 caracteres")
    @NotEmpty(message = "La manzana/piso no puede estar vacío")
    private String manzanaPiso;

    @Size(max = 10, message = "La casa/departamento no puede superar los 10 caracteres")
    @NotEmpty(message = "La casa/departamento no puede estar vacío")
    private String casaDepartamento;

    @Size(max = 100, message = "La referencia no puede superar los 100 caracteres")
    @NotEmpty(message = "La referencia no puede estar vacío")
    private String referencia;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private Boolean eliminado;

    @NotNull(message = "El campo es requerido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_localidad", nullable = false)
    private Localidad localidad;
}
