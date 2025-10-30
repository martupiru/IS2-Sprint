package com.sprint.tinder.entities;

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
@Table(name="voto")
@Entity
public class Voto {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Temporal(TemporalType.TIMESTAMP)
    private Date respuesta;

    @ManyToOne
    private Mascota mascota1;
    @ManyToOne
    private Mascota mascota2;
    private boolean eliminado;
}