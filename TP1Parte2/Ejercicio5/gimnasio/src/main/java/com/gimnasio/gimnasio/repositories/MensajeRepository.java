package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Mensaje;
import com.gimnasio.gimnasio.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, String> {
    // Lista los mensajes activos
    @Query(value = "SELECT * FROM mensajes WHERE eliminado = false", nativeQuery = true)
    List<Mensaje> findAllByEliminadoFalse();

    // Encuentra un mensaje activo por id
    @Query(value = "SELECT * FROM mensajes WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Mensaje> findByIdAndEliminadoFalse(@Param("id") String id);

}
