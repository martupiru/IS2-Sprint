package com.sprint.contactos.entities;

import com.sprint.contactos.entities.enums.TipoContacto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contacto")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Contacto extends BaseEntity<String> {

    @Enumerated(EnumType.STRING)
    private TipoContacto tipoContacto; // PERSONAL o LABORAL

    private String observacion;

    // RELACIONES:
    // Contacto puede pertenecer a una Empresa o a una Persona.
    // (en el diagrama parece que ambas usan Contacto)
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}
