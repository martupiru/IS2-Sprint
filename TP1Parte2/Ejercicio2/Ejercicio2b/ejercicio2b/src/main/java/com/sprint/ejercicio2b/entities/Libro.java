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

    @ManyToMany(cascade = CascadeType.REFRESH) // REFRESH es que al momento de persistir un libro, se mantengan actualizados los autores
    private List<Autor> autores;
}
