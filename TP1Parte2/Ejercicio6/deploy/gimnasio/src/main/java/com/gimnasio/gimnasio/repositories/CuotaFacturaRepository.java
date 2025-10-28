package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.CuotaFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CuotaFacturaRepository extends JpaRepository<CuotaFactura, String> {
    @Query(value = "SELECT id_factura FROM cuota_factura WHERE id_cuota = :id", nativeQuery = true)
    String findFacturaIdByCuotaId(@Param("id") String id);
}
