package com.sprint.consultorio.controllers;

import com.sprint.consultorio.entities.Medico;
import com.sprint.consultorio.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medicos")
public class MedicoController implements BaseController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public String listarMedicos(Model model) {
        try {
            model.addAttribute("medicos", medicoService.findAll());
            return "views/medicos/lista";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/nuevo")
    public String nuevoMedico(Model model) {
        model.addAttribute("medico", new Medico());
        return "views/medicos/form";
    }

    @PostMapping("/guardar")
    public String guardarMedico(@ModelAttribute("medico") Medico medico, RedirectAttributes redirect) {
        try {
            medicoService.save(medico);
            redirect.addFlashAttribute("success", "Médico guardado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/medicos";
    }

    @GetMapping("/editar/{id}")
    public String editarMedico(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("medico", medicoService.findById(id));
            return "views/medicos/form";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/medicos";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarMedico(@PathVariable Long id, @ModelAttribute("medico") Medico medico, RedirectAttributes redirect) {
        try {
            medicoService.update(id, medico);
            redirect.addFlashAttribute("success", "Médico actualizado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/medicos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarMedico(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            medicoService.delete(id);
            redirect.addFlashAttribute("success", "Médico eliminado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/medicos";
    }
}
