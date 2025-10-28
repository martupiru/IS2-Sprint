package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
import com.gimnasio.gimnasio.enumerations.Meses;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="cuotas_mensuales")
public class CuotaMensual {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotNull(message = "El mes es obligatorio")
    @Enumerated(EnumType.STRING)
    private Meses mes;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 2000, message = "El año debe ser mayor a 2000")
    private Long anio;

    @NotNull(message = "El estado de la cuota es obligatorio")
    @Enumerated(EnumType.STRING)
    private EstadoCuotaMensual estado;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @NotNull(message = "El campo  no puede ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_valor_cuota", nullable = false)
    private ValorCuota valorCuota;

    @NotNull(message = "El campo  no puede ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_socio", nullable = false)
    private Socio socio;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private boolean eliminado;

}
