package com.sprint.contactos.entities;

import com.sprint.contactos.entities.enums.TipoTelefono;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contacto_telefonico")
@Getter @Setter
public class ContactoTelefonico extends Contacto {

    private String telefono;

    @Enumerated(EnumType.STRING)
    private TipoTelefono tipoTelefono; // FIJO o CELULAR
}

