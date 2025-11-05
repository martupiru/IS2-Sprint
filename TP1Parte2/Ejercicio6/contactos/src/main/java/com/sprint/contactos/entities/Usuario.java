package com.sprint.contactos.entities;

import com.sprint.contactos.entities.enums.Rol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter @Setter
public class Usuario extends BaseEntity<String> {

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private String cuenta;
    private String clave;

    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}
