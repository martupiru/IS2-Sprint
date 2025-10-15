package com.sprint.KioscoPatrones.repositories;

import com.sprint.KioscoPatrones.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,String> {
    @Query("SELECT p FROM Proveedor p WHERE p.eliminado = false")
    List<Proveedor> findAllByEliminadoFalse();

    Optional<Proveedor> findByCuit(String cuit);
}