package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, String> {
    @Query(value = "SELECT * FROM detalle_factura WHERE eliminado = false", nativeQuery = true)
    List<DetalleFactura> findAllByEliminadoFalse();

    @Query(value = "SELECT * FROM detalle_factura WHERE id = :id AND eliminado = false", nativeQuery = true)
    Optional<DetalleFactura> findByIdAndEliminadoFalse(@Param("id") String id);
}
