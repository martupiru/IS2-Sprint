package com.sprint.carrito.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carrito")
@NoArgsConstructor
@AllArgsConstructor
public class Carrito extends BaseEntity<String> {
    private double total;
    @ManyToOne
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @Override
    public String getId() {return id;}

    @Override
    public void setId(String id) {this.id = id;}

    @Override
    public Boolean getEliminado() {return eliminado;}

    @Override
    public void setEliminado(Boolean eliminado) {this.eliminado = eliminado;}

    public double getTotal() {return total;}

    public void setTotal(double total) {this.total = total;}
}
