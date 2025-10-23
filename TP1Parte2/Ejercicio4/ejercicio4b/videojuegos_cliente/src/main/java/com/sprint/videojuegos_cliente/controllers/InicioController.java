package com.sprint.videojuegos_cliente.controllers;

import com.sprint.videojuegos_cliente.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    private final VideojuegoService videojuegoService;

    @Value("${backend.url}")
    private String backendUrl;

    public InicioController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        var lista = videojuegoService.findAll();
        model.addAttribute("videojuegos", lista);
        model.addAttribute("imagesBase", backendUrl + "/images/");
        return "index";
    }
}