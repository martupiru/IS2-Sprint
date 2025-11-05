package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, String> {
    // Lista los mensajes activos
    @Query(value = "SELECT * FROM facturas WHERE eliminado = false", nativeQuery = true)
    List<Factura> findAllByEliminadoFalse();

    // Encuentra un mensaje activo por id
    @Query(value = "SELECT * FROM facturas WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<Factura> findByIdAndEliminadoFalse(@Param("id") String id);

    @Query("SELECT MAX(f.numeroFactura) FROM Factura f")
    Long findUltimoNumeroFactura();
}
