package entity;
import javax.persistence.*;


import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombreCalle;
    private int numero;
    private boolean eliminado;
    @OneToOne
    @JoinColumn
    private Cliente cliente;

    public Domicilio(String nombreCalle, int numero, boolean eliminado, Cliente cliente) {
        this.nombreCalle = nombreCalle;
        this.numero = numero;
        this.eliminado = eliminado;
        this.cliente = cliente;
    }
}
