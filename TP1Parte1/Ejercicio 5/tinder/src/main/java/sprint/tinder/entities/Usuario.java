package sprint.tinder.entities;

import jakarta.persistence.*;

import java.util.Date;

import org.hibernate.annotations.UuidGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="usuario")
@Entity
public class Usuario {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private String nombre;
    private String apellido;
    private String mail;
    private String clave;
    @ManyToOne
    private Zona zona;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal (TemporalType.TIMESTAMP)
    private Date baja;

    @OneToOne
    private Foto foto;

    private boolean eliminado;
}

