package entity;


import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {
    private String apellido;
    private int dni;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Domicilio domicilio;
    private String nombre;
    private boolean eliminado;
    @OneToMany( cascade = CascadeType.ALL)
    private List<Factura> facturas = new ArrayList<>();

    public Cliente(String apellido, int dni, Domicilio domicilio, String nombre, boolean eliminado, List<Factura> facturas) {
        this.apellido = apellido;
        this.dni = dni;
        this.domicilio = domicilio;
        this.nombre = nombre;
        this.eliminado = eliminado;
        this.facturas = facturas;
    }
}

