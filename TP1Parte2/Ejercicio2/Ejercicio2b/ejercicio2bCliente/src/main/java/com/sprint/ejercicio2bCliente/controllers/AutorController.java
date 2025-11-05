package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.DTO.AutorBasicoDTO;
import com.sprint.ejercicio2bCliente.DTO.AutorLibroDTO;
import com.sprint.ejercicio2bCliente.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public String listar(Model model) {
        List<AutorBasicoDTO> autores = autorService.findAll();
        model.addAttribute("autores", autores);
        return "autores";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        AutorLibroDTO autor = autorService.findById(id);
        model.addAttribute("autor", autor);
        return "autor-detalle";
    }

    @GetMapping("/nuevo")
    public String nuevoAutor(Model model) {
        model.addAttribute("autor", new AutorLibroDTO());
        return "autor-form";
    }

    @GetMapping("/editar/{id}")
    public String editarAutor(@PathVariable Long id, Model model) {
        AutorLibroDTO autor = autorService.findById(id);
        model.addAttribute("autor", autor);
        return "autor-form";
    }

    @PostMapping
    public String guardar(@ModelAttribute AutorLibroDTO autor) {
        if (autor.getId() != null) {
            autorService.update(autor.getId(), autor);
        } else {
            autorService.save(autor);
        }
        return "redirect:/autores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        autorService.delete(id);
        return "redirect:/autores";
    }
}
