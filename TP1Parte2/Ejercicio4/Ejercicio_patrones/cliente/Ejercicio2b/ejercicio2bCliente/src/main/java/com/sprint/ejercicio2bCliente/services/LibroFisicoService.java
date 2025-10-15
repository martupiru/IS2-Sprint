package com.sprint.ejercicio2bCliente.services;

import com.sprint.ejercicio2bCliente.DTO.LibroFisicoDTO;
import org.springframework.stereotype.Service;

@Service
public class LibroFisicoService extends BaseServiceClient<LibroFisicoDTO, Long> {
    public LibroFisicoService() {
        super("http://localhost:9000/api/v1/libros-fisicos", LibroFisicoDTO.class);
    }
}