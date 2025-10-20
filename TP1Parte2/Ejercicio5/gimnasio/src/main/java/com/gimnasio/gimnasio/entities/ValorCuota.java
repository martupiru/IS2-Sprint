package com.gimnasio.gimnasio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "valor_cuotas")
public class ValorCuota {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private Date fechaDesde;
    private Date fechaHasta;
    private double valorCuota;
    private boolean eliminado;
}
