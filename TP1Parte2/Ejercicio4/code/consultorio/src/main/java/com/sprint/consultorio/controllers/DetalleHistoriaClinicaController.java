package com.sprint.consultorio.controllers;

import com.sprint.consultorio.entities.DetalleHistoriaClinica;
import com.sprint.consultorio.entities.HistoriaClinica;
import com.sprint.consultorio.services.DetalleHistoriaClinicaService;
import com.sprint.consultorio.services.HistoriaClinicaService;
import com.sprint.consultorio.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/detalles")
public class DetalleHistoriaClinicaController implements BaseController{

    @Autowired
    private DetalleHistoriaClinicaService detalleService;

    @Autowired
    private HistoriaClinicaService historiaService;

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/nuevo/{idHistoria}")
    public String nuevoDetalle(@PathVariable Long idHistoria, Model model, RedirectAttributes redirect) {
        try {
            HistoriaClinica historia = historiaService.findById(idHistoria);
            model.addAttribute("detalle", new DetalleHistoriaClinica());
            model.addAttribute("historia", historia);
            model.addAttribute("medicos", medicoService.findAll());
            return "views/detalles/form";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/historias";
        }
    }

    @PostMapping("/guardar/{idHistoria}")
    public String guardarDetalle(@PathVariable Long idHistoria,
                                 @ModelAttribute("detalle") DetalleHistoriaClinica detalle,
                                 RedirectAttributes redirect) {
        try {
            HistoriaClinica historia = historiaService.findById(idHistoria);
            detalle.setHistoriaClinica(historia);
            detalleService.save(detalle);
            redirect.addFlashAttribute("success", "Detalle agregado correctamente.");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/historias/ver/" + idHistoria;
    }

    @GetMapping("/editar/{id}")
    public String editarDetalle(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            DetalleHistoriaClinica detalle = detalleService.findById(id);
            model.addAttribute("detalle", detalle);
            model.addAttribute("historia", detalle.getHistoriaClinica());
            model.addAttribute("medicos", medicoService.findAll());
            return "views/detalles/form"; // reutiliza el mismo form
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/historias";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarDetalle(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            DetalleHistoriaClinica detalle = detalleService.findById(id);
            Long idHistoria = detalle.getHistoriaClinica().getId();
            detalleService.delete(id);
            redirect.addFlashAttribute("success", "Detalle eliminado correctamente.");
            return "redirect:/historias/ver/" + idHistoria;
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/historias";
        }
    }
}

