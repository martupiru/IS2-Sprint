package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {

    @Query("SELECT s FROM Socio s WHERE s.eliminado = false")
    List<Socio> findAllByEliminadoFalse();

    @Query("SELECT s FROM Socio s WHERE s.id = :id AND s.eliminado = false")
    Optional<Socio> findByIdAndEliminadoFalse(@Param("id") String id);

    boolean existsByNumeroSocio(Long numeroSocio); // Comprobar unicidad del num de socio

    @Query("SELECT MAX(s.numeroSocio) FROM Socio s")
    Long findUltimoNumeroSocio();

    @Query("SELECT s FROM Socio s WHERE s.eliminado = false")
    List<Socio> findAllNoEliminados();

    // Busca socios que cumplan años para mandar el mail
    @Query("SELECT s FROM Socio s WHERE DAY(s.fechaNacimiento) = :day AND MONTH(s.fechaNacimiento) = :month AND s.eliminado = false")
    List<Socio> findSociosCumpleaniosHoy(@Param("day") int day, @Param("month") int month);

    // Contar socios activos
    @Query("SELECT COUNT(s) FROM Socio s WHERE s.eliminado = false")
    long contarSociosActivos();

    // Contar todos los socios
    @Query("SELECT COUNT(s) FROM Socio s")
    long contarSocios();

    @Query("SELECT s FROM Socio s WHERE s.eliminado = true")
    List<Socio> findAllByEliminadoTrue();

    Optional<Socio> findByNumeroSocio(Long numeroSocio);


}
