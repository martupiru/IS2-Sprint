package entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String denominacion;
    @ManyToMany(mappedBy = "categorias", cascade = CascadeType.ALL)
    private Collection<Articulo> articulos;

    public Categoria(String denominacion, Collection<Articulo> articulos) {
        this.denominacion = denominacion;
        this.articulos = articulos;
    }
}
