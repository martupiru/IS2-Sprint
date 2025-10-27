package sprint.tinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sprint.tinder.entities.Foto;

public interface FotoRepository extends JpaRepository<Foto, String> {

}
