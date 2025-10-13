package com.sprint.carrito.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity<ID> {

    @Id
    @UuidGenerator
    protected String id;
    protected Boolean eliminado = false;

    public abstract ID getId();
    public abstract void setId(ID id);
    public abstract Boolean getEliminado();
    public abstract void setEliminado(Boolean eliminado);

}
