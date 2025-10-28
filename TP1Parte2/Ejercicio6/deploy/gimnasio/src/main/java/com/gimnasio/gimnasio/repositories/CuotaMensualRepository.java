package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
import com.gimnasio.gimnasio.enumerations.Meses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuotaMensualRepository extends JpaRepository<CuotaMensual, String> {

    List<CuotaMensual> findAllByEliminadoFalse();

    Optional<CuotaMensual> findByIdAndEliminadoFalse(String id);

    List<CuotaMensual> findByEstadoAndEliminadoFalse(EstadoCuotaMensual estado);
    @Query("SELECT c FROM CuotaMensual c " +
            "WHERE c.fechaVencimiento BETWEEN :fechaDesde AND :fechaHasta " +
            "AND c.eliminado = false")
    List<CuotaMensual> findByFechaVencimientoBetweenAndEliminadoFalse(
            @Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta);

    List<CuotaMensual> findBySocioNumeroSocioAndEliminadoFalse(Long numeroSocio);

    List<CuotaMensual> findBySocioAndEstadoAndEliminadoFalse(Socio socio, EstadoCuotaMensual estado);

    List<CuotaMensual> findByMesAndAnioAndSocioInAndEliminadoFalse(Meses mes, Long anio, List<Socio> socios);

    List<CuotaMensual> findByMesAndAnioAndEliminadoFalse(Meses mes, long anio);

}
