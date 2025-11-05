package com.sprint.contactos.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "persona")
@Getter @Setter
public class Persona extends BaseEntity<String> {

    private String nombre;
    private String apellido;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<Contacto> contactos;

}
