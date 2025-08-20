package entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int cantidad;
    private int subtotal;
    @ManyToOne
    private Articulo articulo;

    @ManyToOne
    private Factura factura;

    public DetalleFactura(Factura factura, Articulo articulo, int subtotal, int cantidad) {
        this.factura = factura;
        this.articulo = articulo;
        this.subtotal = subtotal;
        this.cantidad = cantidad;
    }
}
