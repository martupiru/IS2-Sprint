package com.example.demo.api;

import com.example.demo.entities.Autor;
import com.example.demo.services.AutorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/autores")
public class AutorControllerApi extends BaseControllerApi<Autor, Long> {

    public AutorControllerApi(AutorService service) {
        super(service);
    }
}
