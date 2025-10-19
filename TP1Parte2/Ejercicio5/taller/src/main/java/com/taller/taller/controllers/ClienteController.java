package com.taller.taller.controllers;

import com.taller.taller.entities.Cliente;
import com.taller.taller.services.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteController extends BaseController<Cliente, String> {

    public ClienteController(ClienteService service) {
        super(service);
        initController(new Cliente(), "Listado de Clientes", "Editar Cliente");
    }
}

