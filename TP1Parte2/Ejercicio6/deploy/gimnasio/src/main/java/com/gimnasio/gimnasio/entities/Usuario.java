package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.RolUsuario;
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
public class Usuario {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotEmpty(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50, message = "El nombre de usuario no puede superar los 50 caracteres")
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @NotEmpty(message = "La clave no puede estar vacía")
    private String clave;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private boolean eliminado;

    @NotNull(message = "El campo rutina es requerido")
    @ManyToOne
    @JoinColumn(name = "fk_persona", nullable = false)
    private Persona persona;
}
