package entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fecha;
    @ManyToOne
    @JoinColumn
    private Cliente cliente;
    private int numero;
    private int total;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true) // lo del rombo
    private Collection<DetalleFactura> detalleFacturas;


    public Factura(String fecha, Cliente cliente, int numero, int total, Collection<DetalleFactura> detalleFacturas) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.numero = numero;
        this.total = total;
        this.detalleFacturas = detalleFacturas;
    }
}
