package com.sprint.carrito.repositories;

import com.sprint.carrito.entities.Carrito;

import java.util.Optional;

public interface CarritoRepository extends BaseRepository<Carrito, String>{
    Optional<Carrito> findByUsuarioIdAndEliminadoFalse(String idUsuario);
}
