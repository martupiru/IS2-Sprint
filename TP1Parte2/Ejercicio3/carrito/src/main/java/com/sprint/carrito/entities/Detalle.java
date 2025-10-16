package com.sprint.carrito.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detalle extends BaseEntity<String>{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    public Integer cantidad = 1; // por defecto

    @ManyToOne
    @JoinColumn(name = "fk_articulo")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "fk_carrito", nullable = false)
    private Carrito carrito;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Boolean getEliminado() {
        return eliminado;
    }

    @Override
    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

}
