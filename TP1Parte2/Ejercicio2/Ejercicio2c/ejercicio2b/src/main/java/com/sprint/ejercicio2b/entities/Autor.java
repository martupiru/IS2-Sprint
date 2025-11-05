package com.sprint.ejercicio2b.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Table(name = "autor")
public class Autor extends Base{
    private String nombre;
    private String apellido;
    @Column(length = 1500)
    private String biografia;
}
