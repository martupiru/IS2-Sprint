package com.sprint.consultorio.controllers;

import com.sprint.consultorio.entities.Paciente;
import com.sprint.consultorio.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes")
public class PacienteController implements BaseController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public String listarPacientes(Model model) {
        try {
            model.addAttribute("pacientes", pacienteService.findAll());
            return "views/pacientes/lista"; // ← archivo Thymeleaf
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/nuevo")
    public String nuevoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "views/pacientes/form";
    }

    @PostMapping("/guardar")
    public String guardarPaciente(@ModelAttribute("paciente") Paciente paciente, RedirectAttributes redirect) {
        try {
            pacienteService.save(paciente);
            redirect.addFlashAttribute("success", "Paciente guardado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pacientes";
    }

    @GetMapping("/editar/{id}")
    public String editarPaciente(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("paciente", pacienteService.findById(id));
            return "views/pacientes/form";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/pacientes";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarPaciente(@PathVariable Long id, @ModelAttribute("paciente") Paciente paciente, RedirectAttributes redirect) {
        try {
            pacienteService.update(id, paciente);
            redirect.addFlashAttribute("success", "Paciente actualizado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pacientes";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            pacienteService.delete(id);
            redirect.addFlashAttribute("success", "Paciente eliminado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pacientes";
    }
}