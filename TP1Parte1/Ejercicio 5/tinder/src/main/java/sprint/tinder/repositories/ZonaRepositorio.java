package sprint.tinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sprint.tinder.entities.Zona;

import java.util.Collection;

@Repository
public interface ZonaRepositorio extends JpaRepository<Zona,String> {
    @Query("SELECT c FROM Zona c WHERE c.nombre = :nombre AND c.eliminado = FALSE")
    public Zona buscarZonaPorNombre(@Param("nombre")String id);

    @Query("SELECT c FROM Zona c WHERE c.eliminado = FALSE")
    public Collection<Zona> listarZonaActiva();
}
