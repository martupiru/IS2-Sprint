package com.sprint.videojuegos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria extends BaseEntity<Long> {

    private String nombre;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Videojuego> videojuegos;

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
