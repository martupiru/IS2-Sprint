package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.DTO.LocalidadBasicaDTO;
import com.sprint.ejercicio2bCliente.DTO.LocalidadDTO;
import com.sprint.ejercicio2bCliente.services.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/localidades")
public class LocalidadController {
    @Autowired
    private LocalidadService localidadService;

    @GetMapping
    public String listar(Model model) {
        List<LocalidadBasicaDTO> localidades = localidadService.findAll();
        model.addAttribute("localidades", localidades);
        return "localidades";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        LocalidadDTO localidad = localidadService.findById(id);
        model.addAttribute("localidad", localidad);
        return "localidad-detalle";
    }

    @GetMapping("/nueva")
    public String nuevaLocalidad(Model model) {
        model.addAttribute("localidad", new LocalidadDTO());
        return "localidad-form";
    }

    @GetMapping("/editar/{id}")
    public String editarLocalidad(@PathVariable Long id, Model model) {
        LocalidadDTO localidad = localidadService.findById(id);
        model.addAttribute("localidad", localidad);
        return "localidad-form";
    }

    @PostMapping
    public String guardar(@ModelAttribute LocalidadDTO localidad) {
        localidadService.save(localidad);
        return "redirect:/localidades";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        localidadService.delete(id);
        return "redirect:/localidades";
    }
}
