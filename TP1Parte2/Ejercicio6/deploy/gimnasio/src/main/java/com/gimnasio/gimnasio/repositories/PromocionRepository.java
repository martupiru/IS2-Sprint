package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Mensaje;
import com.gimnasio.gimnasio.entities.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, String> {
    // Lista las promociones activas
    @Query("SELECT p FROM Promocion p WHERE p.eliminado = false")
    List<Promocion> findAllByEliminadoFalse();

    // Encuentra una promocion activa
    @Query(value = "SELECT * FROM promociones WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Promocion> findByIdAndEliminadoFalse(@Param("id") String id);

    @Query("SELECT p FROM Promocion p " + "WHERE p.fechaEnvioPromocion BETWEEN :inicio AND :fin " + "AND p.eliminado = false")
    List<Promocion> findByFechaEnvioPromocionBetween(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT p FROM Promocion p " + "WHERE p.fechaEnvioPromocion = :fecha " + "AND p.eliminado = false")
    List<Promocion> findByFechaEnvioPromocion(@Param("fecha") LocalDate fecha);

}
