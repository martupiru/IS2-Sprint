package entity;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {
    private String apellido;
    private int dni;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Domicilio domicilio;
    private String nombre;
    private boolean eliminado;

    public Cliente(String apellido, int dni, Domicilio domicilio, String nombre, boolean eliminado) {
        this.apellido = apellido;
        this.dni = dni;
        this.domicilio = domicilio;
        this.nombre = nombre;
        this.eliminado = eliminado;
    }
}

