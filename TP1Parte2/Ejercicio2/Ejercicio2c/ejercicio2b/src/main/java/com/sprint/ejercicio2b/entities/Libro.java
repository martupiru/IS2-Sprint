package com.sprint.ejercicio2b.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Table(name= "libro")
public class Libro extends Base{

    private String titulo;
    private int fecha;
    private String genero;
    private int paginas;

    @Column(length = 500)
    private String rutaArchivo; // ruta para el pdf

    @ManyToMany(cascade = CascadeType.REFRESH)
    private List<Autor> autores;
}
