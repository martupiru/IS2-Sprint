package com.taller.taller.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "historial_arreglos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialArreglo extends BaseEntity<String> {

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaArreglo;

    private String detalleArreglo;

    @ManyToOne
    @JoinColumn(name = "mecanico_id")
    private Mecanico mecanico;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Boolean getEliminado() {
        return this.eliminado;
    }

    @Override
    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}


