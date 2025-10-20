package com.gimnasio.gimnasio.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
public class DetalleFactura {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

//    @NotNull(message = "El campo factura es requerido")
//    @ManyToOne
//    @JoinColumn(name = "fk_factura", nullable = false)
//    private Factura factura;

    @NotNull(message = "El campo cuota es requerido")
    @ManyToOne
    @JoinColumn(name = "fk_cuota_mensual")
    private CuotaMensual cuotaMensual;

    @NotNull(message = "El campo eliminado no puede ser nulo")
    private boolean eliminado;
}
