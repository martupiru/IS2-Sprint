package com.sprint.carrito.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "imagen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Imagen extends BaseEntity<String> {
    private String nombre;
    private String mime;
    private byte[] contenido;

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

    public String getMime() {return mime;}

    public void setMime(String mime) {this.mime = mime;}

    public byte[] getContenido() {return contenido;}

    public void setContenido(byte[] contenido) {this.contenido = contenido;}

}
