package com.sprint.carrito.services;

import com.sprint.carrito.entities.Carrito;
import com.sprint.carrito.repositories.CarritoRepository;
import org.springframework.stereotype.Service;

@Service
public class CarritoService extends BaseService<Carrito,String>{
    protected CarritoService(CarritoRepository repository) {
        super(repository);
    }
}
