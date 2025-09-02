package sprint.tinder.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="voto")
@Entity
public class Voto {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Temporal(TemporalType.TIMESTAMP)
    private Date respuesta;

    @ManyToOne
    private Mascota mascota1;
    @ManyToOne
    private Mascota mascota2;
}
