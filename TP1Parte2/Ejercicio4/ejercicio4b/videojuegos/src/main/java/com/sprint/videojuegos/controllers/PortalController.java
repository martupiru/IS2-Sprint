package com.sprint.videojuegos.controllers;

import com.sprint.videojuegos.entities.Videojuego;
import com.sprint.videojuegos.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PortalController {

    private final VideojuegoService videojuegoService;

    @Autowired
    public PortalController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping({"/", "/inicio"})
    public String inicio(Model model) {
        try {
            List<Videojuego> videojuegos = videojuegoService.listarActivos();
            model.addAttribute("videojuegos", videojuegos);
            return "views/inicio";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable("id") Long id, Model model) {
        try {
            Videojuego v = videojuegoService.getOne(id);
            model.addAttribute("videojuego", v);
            return "views/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/busqueda")
    public String busqueda(@RequestParam(name = "query", required = false) String q, Model model) {
        try {
            List<Videojuego> videojuegos = videojuegoService.findByTitle(q);
            model.addAttribute("videojuegos", videojuegos);
            return "views/busqueda";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}

