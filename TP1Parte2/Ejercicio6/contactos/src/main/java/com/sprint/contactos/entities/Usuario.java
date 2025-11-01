package com.sprint.contactos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter @Setter
public class Usuario extends BaseEntity<String> {

    private String cuenta;
    private String clave;

    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}
