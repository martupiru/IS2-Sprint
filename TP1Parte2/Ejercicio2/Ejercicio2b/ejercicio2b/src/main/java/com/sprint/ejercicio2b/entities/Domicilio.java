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
@Table(name = "domicilio")
public class Domicilio extends Base {

    private String calle;
    private int numero;

    @ManyToOne(optional = false) // un domicilio pertenece a una localidad, no puede ser nulo
    @JoinColumn(name = "fk_localidad")
    private Localidad localidad;

}
