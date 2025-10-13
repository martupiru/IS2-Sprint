package com.example.demo.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Libro extends BaseEntity<Long> {

    private String titulo;
    private String autor;
    private String genero;
    private int fecha;
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