package sprint.tinder.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="foto")
public class Foto {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private String nombre;
    private String mime; //mime le da formato de archivo a la foto para que el navegador la interprete correctamente
    @Lob @Basic(fetch = FetchType.LAZY)
    // Lob= Le indica que es un tipo de dato grande o pesado, y es para que la base de datos lo guarde en un tipo de dato específico para almacenarlo
    // @Basic(fetch = FetchType.LAZY) = Indica que no queremos que se cargue inmediatamente la foto (si ponemos eager en vez de lazy es al revés), y esto hace los queries mas livianos
    // Entonces solo va a buscar ese contenido a la base de datos cuando yo haga un get, sino solo va a traer inmediatamente los demás atributos de la foto.
    private byte[] conenido;


}
