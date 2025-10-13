package com.sprint.ejercicio2bCliente.controllers;



import com.sprint.ejercicio2bCliente.DTO.AutorDTO;
import com.sprint.ejercicio2bCliente.services.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autor")
public class AutorControllerGeneric extends BaseController<AutorDTO, Long> {

    public AutorControllerGeneric(AutorService service) {
        super(service);
        initController(new AutorDTO(), "LIST AUTOR", "EDIT AUTOR");
    }
}

