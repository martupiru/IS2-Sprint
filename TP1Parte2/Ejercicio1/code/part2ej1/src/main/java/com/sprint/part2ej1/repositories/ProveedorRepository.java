package com.sprint.part2ej1.repositories;

import com.sprint.part2ej1.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,String> {
    @Query("SELECT p FROM Proveedor p WHERE p.eliminado = false")
    List<Proveedor> findAllByEliminadoFalse();
}