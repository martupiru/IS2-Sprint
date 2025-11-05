package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.DTO.DomicilioBasicoDTO;
import com.sprint.ejercicio2bCliente.DTO.DomicilioDTO;
import com.sprint.ejercicio2bCliente.DTO.LocalidadBasicaDTO;
import com.sprint.ejercicio2bCliente.services.DomicilioService;
import com.sprint.ejercicio2bCliente.services.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/domicilios")
public class DomicilioController {
    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private LocalidadService localidadService;

    @GetMapping
    public String listar(Model model) {
        List<DomicilioBasicoDTO> domicilios = domicilioService.findAll();
        model.addAttribute("domicilios", domicilios);
        return "domicilios";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        DomicilioDTO domicilio = domicilioService.findById(id);
        model.addAttribute("domicilio", domicilio);
        return "domicilio-detalle";
    }

    @GetMapping("/nuevo")
    public String nuevoDomicilio(Model model) {
        model.addAttribute("domicilio", new DomicilioDTO());
        List<LocalidadBasicaDTO> localidades = localidadService.findAll();
        model.addAttribute("localidades", localidades);
        return "domicilio-form";
    }

    @GetMapping("/editar/{id}")
    public String editarDomicilio(@PathVariable Long id, Model model) {
        DomicilioDTO domicilio = domicilioService.findById(id);
        model.addAttribute("domicilio", domicilio);
        List<LocalidadBasicaDTO> localidades = localidadService.findAll();
        model.addAttribute("localidades", localidades);
        return "domicilio-form";
    }

    @PostMapping
    public String guardar(@ModelAttribute DomicilioDTO domicilio) {
        domicilioService.save(domicilio);
        return "redirect:/domicilios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        domicilioService.delete(id);
        return "redirect:/domicilios";
    }
}
