package com.sprint.ejercicio2bCliente.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/subpage")
    public String subpage() {
        return "subpage";
    }
}
