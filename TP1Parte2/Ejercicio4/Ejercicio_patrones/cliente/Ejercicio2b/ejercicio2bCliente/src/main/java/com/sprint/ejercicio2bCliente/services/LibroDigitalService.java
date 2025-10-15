package com.sprint.ejercicio2bCliente.services;

import com.sprint.ejercicio2bCliente.DTO.LibroDigitalDTO;
import org.springframework.stereotype.Service;

@Service
public class LibroDigitalService extends BaseServiceClient<LibroDigitalDTO, Long> {
    public LibroDigitalService() {
        super("http://localhost:9000/api/v1/libros-digitales", LibroDigitalDTO.class);
    }
}