package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.TipoDocumento;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED) // Para herencia
public class Persona {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotEmpty(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Temporal(TemporalType.DATE)
    private LocalDate fechaNacimiento;

    @NotNull(message = "El tipo de documento es obligatorio")
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @NotEmpty(message = "El número de documento no puede estar vacío")
    @Size(max = 30, message = "El número de documento no puede superar los 30 caracteres")
    private String numeroDocumento;

    @NotEmpty(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "El teléfono debe contener solo números y puede incluir el prefijo internacional")
    private String telefono;

    @NotEmpty(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String correoElectronico;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private Boolean eliminado = false;

    @NotNull(message = "El campo es requerido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_direccion", nullable = false)
    private Direccion direccion;

    @NotNull(message = "El campo es requerido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_sucursal", nullable = false)
    private Sucursal sucursal;

}

