package com.example.demo.api;

import com.example.demo.entities.LibroDigital;
import com.example.demo.services.LibroDigitalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/libros-digitales")
public class LibroDigitalControllerApi extends BaseControllerApi<LibroDigital, Long> {

    public LibroDigitalControllerApi(LibroDigitalService service) {
        super(service);
    }
}
