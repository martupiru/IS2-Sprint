package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.TipoMensaje;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Table(name = "mensajes")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // Para herencia
public class Mensaje {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotEmpty(message = "El título no puede estar vacío")
    private String titulo;

    @NotEmpty(message = "El texto no puede estar vacío")
    private String texto;

    @NotNull(message = "El tipo de mensaje es obligatorio")
    @Enumerated(EnumType.STRING)
    private TipoMensaje tipoMensaje;

    @NotNull(message = "El campo es requerido")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_usuario", nullable = false)
    private Usuario usuario;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private boolean eliminado;
}
