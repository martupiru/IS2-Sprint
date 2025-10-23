package com.sprint.videojuegos.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "videojuegos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Videojuego extends BaseEntity<Long> {

    @NotEmpty(message = "{NotEmpty.Videojuego.titulo}")
    private String titulo;
    @Size(min=5,max=300,message= "La descripcion debe ser entre 5 y 300 caracteres")
    private String descripcion;

    private String imagen;

    @Min(value = 5,message="El precio debe tener un minimo de 5")
    @Max(value = 10000, message="El precio debe ser menor a 1000")
    private float precio;
    @Min(value = 0,message="El stock debe ser mayor o igual a 0")
    @Max(value = 10000, message="El stock debe ser menor a 1000")
    private short stock;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message="No puede ser nulo la fecha")
    @PastOrPresent(message="Debe ser igual o menor a la fecha de hoy")
    private Date fechaLanzamiento;

    @NotNull(message="Es requerido el estudio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_estudio", nullable = false)
    private Estudio estudio;

    @NotNull(message="Es requerida la categoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_categoria", nullable = false)
    private Categoria categoria;

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