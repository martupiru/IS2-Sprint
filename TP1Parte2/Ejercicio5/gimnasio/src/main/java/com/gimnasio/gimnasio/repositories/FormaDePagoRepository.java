package com.gimnasio.gimnasio.repositories;

import com.gimnasio.gimnasio.entities.FormaDePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaDePagoRepository extends JpaRepository<FormaDePago, String> {
}
