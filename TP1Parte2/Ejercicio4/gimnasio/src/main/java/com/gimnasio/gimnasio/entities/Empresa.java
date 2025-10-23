package com.gimnasio.gimnasio.entities;


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


    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotEmpty(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "El teléfono debe contener solo números y puede incluir el prefijo internacional")
    private String telefono;

    @NotEmpty(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String correoElectronico;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private Boolean eliminado = false;
}
