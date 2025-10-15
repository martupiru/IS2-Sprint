package com.sprint.ejercicio2bCliente.services;

import com.sprint.ejercicio2bCliente.DTO.AutorDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroDTO;
import org.springframework.stereotype.Service;

@Service
public class LibroService extends BaseServiceClient<LibroDTO, Long> {
    public LibroService() {
        super("http://localhost:9000/api/v1/libros", LibroDTO.class);
    }
}
