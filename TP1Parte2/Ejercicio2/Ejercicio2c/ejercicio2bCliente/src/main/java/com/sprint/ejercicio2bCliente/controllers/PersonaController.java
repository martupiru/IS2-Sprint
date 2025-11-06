package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.DTO.PersonaBasicaDTO;
import com.sprint.ejercicio2bCliente.DTO.PersonaDTO;
import com.sprint.ejercicio2bCliente.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaClientService;

    @GetMapping
    public String listar(Model model) {
        List<PersonaBasicaDTO> personas = personaClientService.findAll();
        model.addAttribute("personas", personas);
        return "personas";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        PersonaDTO persona = personaClientService.findById(id);
        model.addAttribute("persona", persona);
        return "persona-detalle";
    }

    @GetMapping("/nueva")
    public String nuevaPersona(Model model) {
        model.addAttribute("persona", new PersonaDTO());
        return "persona-form";
    }

    @GetMapping("/editar/{id}")
    public String editarPersona(@PathVariable Long id, Model model) {
        PersonaDTO persona = personaClientService.findById(id);
        model.addAttribute("persona", persona);
        return "persona-form";
    }

    @PostMapping
    public String guardar(@ModelAttribute PersonaDTO persona) {
        if (persona.getId() != null) {
            personaClientService.update(persona.getId(), persona); //editar
        } else {
            personaClientService.save(persona); //guardar nueva persona
        }
        return "redirect:/personas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        personaClientService.delete(id);
        return "redirect:/personas";
    }
}
