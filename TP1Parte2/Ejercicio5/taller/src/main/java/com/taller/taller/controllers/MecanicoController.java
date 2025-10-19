package com.taller.taller.controllers;

import com.taller.taller.entities.Mecanico;
import com.taller.taller.services.MecanicoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mecanico")
public class MecanicoController extends BaseController<Mecanico, String> {

    public MecanicoController(MecanicoService service) {
        super(service);
        initController(new Mecanico(), "Listado de Mecánicos", "Editar Mecánico");
    }
}

