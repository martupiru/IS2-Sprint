package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.TipoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Table(name = "forma_de_pago")
public class FormaDePago {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    @NotNull(message = "El tipo de pago es obligatorio")
    @Enumerated(EnumType.STRING)
    private TipoPago tipoPago;
    private String observacion;
    @NotNull(message = "El campo eliminado no puede ser nulo")
    private boolean eliminado;
}
