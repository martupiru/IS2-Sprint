package com.example.demo.api;

import com.example.demo.entities.LibroFisico;
import com.example.demo.services.LibroFisicoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/libros-fisicos")
public class LibroFisicoControllerApi extends BaseControllerApi<LibroFisico, Long> {

    public LibroFisicoControllerApi(LibroFisicoService service) {
        super(service);
    }
}
