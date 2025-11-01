package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.Persona;
import com.sprint.contactos.services.PersonaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persona")
public class PersonaController extends BaseController<Persona, String> {

    public PersonaController(PersonaService service) {
        super(service);
        initController(
                new Persona(),
                "Listado de Personas",
                "Persona"
        );
    }
}
