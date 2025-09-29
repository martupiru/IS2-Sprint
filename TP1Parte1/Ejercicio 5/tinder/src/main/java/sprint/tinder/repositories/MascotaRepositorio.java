package sprint.tinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sprint.tinder.entities.Mascota;
import java.util.List;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota,String> {
    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id AND c.baja IS NULL")
    public List<Mascota> listarMascotasPorUsuario(@Param("id")String id);

    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id AND c.nombre = :nombre AND c.baja IS NULL")
    public Mascota buscarMascota(@Param("id")String id, @Param("nombre")String nombre);

    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id AND c.baja IS NOT NULL")
    public List<Mascota> listarMascotasDeBaja(@Param("id")String id);
}
