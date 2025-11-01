package com.sprint.contactos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "persona")
@Getter @Setter
public class Persona extends BaseEntity<String> {

    private String nombre;
    private String apellido;

    // eliminado viene de BaseEntity
}
