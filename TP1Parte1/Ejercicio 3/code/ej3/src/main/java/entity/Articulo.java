package entity;
import javax.persistence.*;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int cantidad;
    private String denominacion;
    private int precio;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private Collection<DetalleFactura> detalleFacturas2;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "articulo_categoria",
            joinColumns = @JoinColumn(
                    name = "articulo_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "categoria_id",
                    referencedColumnName = "id"
            )
    )
    private Collection<Categoria> categorias;


    public Articulo(int cantidad, String denominacion, int precio, Collection<DetalleFactura> detalleFacturas2, Collection<Categoria> categorias) {
        this.cantidad = cantidad;
        this.denominacion = denominacion;
        this.precio = precio;
        this.detalleFacturas2 = detalleFacturas2;
        this.categorias = categorias;
    }


    public Articulo(int precio, String denominacion, int cantidad) {
        this.precio = precio;
        this.denominacion = denominacion;
        this.cantidad = cantidad;
    }
}
