package com.sprint.tinder.entities;

import com.sprint.tinder.enumerations.Sexo;
import com.sprint.tinder.enumerations.Tipo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="mascota")
@Entity
public class Mascota {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private String nombre;
    @ManyToOne
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;

    @OneToOne
    private Foto foto;

    private boolean eliminado;
}
