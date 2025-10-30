package com.sprint.tinder.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="zona")
@Entity
public class Zona {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private String nombre;
    private String descripcion;
    private boolean eliminado;
}
