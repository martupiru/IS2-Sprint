package sprint.tinder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sprint.tinder.entities.Zona;

@Repository
public interface ZonaRepositorio extends JpaRepository<Zona,String> {

}
