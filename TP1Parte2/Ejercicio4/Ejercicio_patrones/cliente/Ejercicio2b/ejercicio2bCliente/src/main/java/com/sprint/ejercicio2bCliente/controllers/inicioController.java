package com.sprint.ejercicio2bCliente.controllers;

import org.springframework.context.annotation.Configuration;import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class inicioController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "Bienvenido al Sistema");
        return "home";
    }
}
