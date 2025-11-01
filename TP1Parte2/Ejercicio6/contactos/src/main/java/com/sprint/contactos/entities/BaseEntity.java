package com.sprint.contactos.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity<ID> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    protected ID id;

    protected Boolean eliminado = false;
}
