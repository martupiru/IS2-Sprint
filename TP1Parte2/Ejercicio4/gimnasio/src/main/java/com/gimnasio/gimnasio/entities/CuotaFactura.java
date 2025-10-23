package com.gimnasio.gimnasio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Table(name="cuota_factura")
public class CuotaFactura {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    private String id_cuota;
    private String id_factura;
}
