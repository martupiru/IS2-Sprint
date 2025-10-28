package com.example.demo.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
public class Libro extends BaseEntity<Long> {

    private String titulo;
    private String autor;
    private String genero;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private int paginas;

    @ManyToMany
    private java.util.List<Autor> autores;

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