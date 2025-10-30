package com.sprint.tinder.repositories;

import com.sprint.tinder.entities.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto,String> {
}
