package com.sprint.carrito.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends BaseEntity<String>{
    private String nombre;
    private String clave;

    @Override
    public String getId() {return id;}

    @Override
    public void setId(String id) {this.id = id;}

    @Override
    public Boolean getEliminado() {return eliminado;}

    @Override
    public void setEliminado(Boolean eliminado) {this.eliminado = eliminado;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getClave() {return clave;}

    public void setClave(String clave) {this.clave = clave;}

}
