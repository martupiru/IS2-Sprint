package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.Usuario;
import com.sprint.contactos.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController extends BaseController<Usuario, String> {

    public UsuarioController(UsuarioService service) {
        super(service);
        initController(
                new Usuario(),
                "Listado de Usuarios",
                "Usuario"
        );
    }
}
