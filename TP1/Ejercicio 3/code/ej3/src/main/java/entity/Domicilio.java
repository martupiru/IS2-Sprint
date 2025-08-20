package entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombreCalle;
    private int numero;
    private boolean eliminado;

    public Domicilio(boolean eliminado, int numero, String nombreCalle) {
        this.eliminado = eliminado;
        this.numero = numero;
        this.nombreCalle = nombreCalle;
    }
}
