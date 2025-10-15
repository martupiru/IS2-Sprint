package com.sprint.ejercicio2bCliente.services;

import com.sprint.ejercicio2bCliente.DTO.AutorDTO;
import org.springframework.stereotype.Service;


@Service
public class AutorService extends BaseServiceClient<AutorDTO, Long> {

    public AutorService() {
        super("http://localhost:9000/api/v1/autores", AutorDTO.class);
    }
}
