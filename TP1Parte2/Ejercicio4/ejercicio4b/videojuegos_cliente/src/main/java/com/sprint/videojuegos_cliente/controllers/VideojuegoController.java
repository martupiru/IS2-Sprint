package com.sprint.videojuegos_cliente.controllers;

import com.sprint.videojuegos_cliente.DTO.VideojuegoDTO;
import com.sprint.videojuegos_cliente.services.VideojuegoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/videojuegos")
public class VideojuegoController {

    @Value("${backend.url}")
    private String backendUrl;

    @Autowired
    private VideojuegoService videojuegoService;

    @GetMapping
    public String listar(Model model) {
        List<VideojuegoDTO> videojuegos = videojuegoService.findAll();
        model.addAttribute("videojuegos", videojuegos);
        model.addAttribute("imagesBase", backendUrl + "/images/");
        return "videojuegos";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        VideojuegoDTO videojuego = videojuegoService.findById(id);
        model.addAttribute("videojuego", videojuego);
        model.addAttribute("imagesBase", backendUrl + "/images/");
        return "videojuego-detalle";
    }

    @GetMapping("/nueva")
    public String nuevoVideojuego(Model model) {
        model.addAttribute("videojuego", new VideojuegoDTO());
        return "videojuego-form";
    }

    @GetMapping("/editar/{id}")
    public String editarVideojuego(@PathVariable Long id, Model model) {
        VideojuegoDTO videojuego = videojuegoService.findById(id);
        model.addAttribute("videojuego", videojuego);
        return "videojuego-form";
    }

    @PostMapping
    public String guardar(@ModelAttribute("videojuego") @Valid VideojuegoDTO videojuego, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("videojuego", videojuego);
            return "videojuego-form";
        }
        if (videojuego.getId() != null) {
            videojuegoService.update(videojuego.getId(), videojuego); //editar
        } else {
            videojuegoService.save(videojuego); //guardar
        }
        return "redirect:/";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        videojuegoService.delete(id);
        return "redirect:/";
    }
}
