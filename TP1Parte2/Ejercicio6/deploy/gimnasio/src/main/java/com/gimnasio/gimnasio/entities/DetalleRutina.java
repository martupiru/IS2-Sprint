package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.EstadoDetalleRutina;
import com.gimnasio.gimnasio.enumerations.EstadoRutina;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalle_rutinas")
public class DetalleRutina {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotNull(message = "La fecha  obligatoria")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotEmpty(message = "La actividad no puede estar vacía")
    @Size(max = 100, message = "La actividad no puede superar los 100 caracteres")
    private String actividad;

    @NotNull(message = "El campo estado del detalle de la rutina es requerido")
    @Enumerated(EnumType.STRING)
    private EstadoDetalleRutina estadoDetalleRutina;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private Boolean eliminado = false;


    @NotNull(message = "El campo rutina es requerido")
    @ManyToOne
    @JoinColumn(name = "fk_rutina", nullable = false)
    private Rutina rutina;

}

