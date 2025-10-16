package com.sprint.carrito.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "imagen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Imagen extends BaseEntity<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String nombre;
    private String mime;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "contenido", columnDefinition = "LONGBLOB")
    private byte[] contenido;

    @Override
    public String getId() {return id;}

    @Override
    public void setId(String id) {this.id = id;}

    @Override
    public Boolean getEliminado() {return eliminado;}

    @Override
    public void setEliminado(Boolean eliminado) {this.eliminado = eliminado;}


}
