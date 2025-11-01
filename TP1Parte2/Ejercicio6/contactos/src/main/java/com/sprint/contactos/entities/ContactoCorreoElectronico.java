package com.sprint.contactos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contacto_correo")
@Getter @Setter
public class ContactoCorreoElectronico extends Contacto {

    private String email;
}

