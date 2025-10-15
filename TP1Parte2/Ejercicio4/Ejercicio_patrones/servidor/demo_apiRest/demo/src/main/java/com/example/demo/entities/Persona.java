package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Persona extends BaseEntity<Long> {


    private String nombre;
    private String apellido;
    private int dni;


    @OneToMany
    private java.util.List<Libro> libros;

    @OneToOne
    private Domicilio domicilio;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
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
