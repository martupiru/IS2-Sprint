package sprint.tinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sprint.tinder.entities.Mascota;
import java.util.List;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota,String> {
    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id")
    public List<Mascota> findByUsuario(@Param("id") String id);
}
