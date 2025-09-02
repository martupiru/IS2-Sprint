package sprint.tinder.entities;

import jakarta.persistence.*;
import sprint.tinder.enumerations.Sexo;

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
@Table(name="mascota")
@Entity
public class Mascota {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private String nombre;
    @ManyToOne
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;

    @OneToOne
    private Foto foto;
}
