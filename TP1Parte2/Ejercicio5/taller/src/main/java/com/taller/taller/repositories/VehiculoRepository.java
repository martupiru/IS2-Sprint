package com.taller.taller.repositories;

import com.taller.taller.entities.Vehiculo;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends BaseRepository<Vehiculo, String> {
}