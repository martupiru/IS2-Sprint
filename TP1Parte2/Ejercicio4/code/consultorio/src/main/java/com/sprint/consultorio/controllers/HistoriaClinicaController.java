package com.sprint.consultorio.controllers;

import com.sprint.consultorio.entities.DetalleHistoriaClinica;
import com.sprint.consultorio.entities.HistoriaClinica;
import com.sprint.consultorio.entities.Paciente;
import com.sprint.consultorio.entities.Usuario;
import com.sprint.consultorio.services.HistoriaClinicaService;
import com.sprint.consultorio.services.PacienteService;
import com.sprint.consultorio.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/historias")
public class HistoriaClinicaController implements BaseController {

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarHistorias(Model model) {
        try {
            model.addAttribute("historias", historiaClinicaService.findAll());
            return "views/historias/lista";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/nuevo")
    public String nuevaHistoria(Model model) {
        try {
            HistoriaClinica historia = new HistoriaClinica();
            historia.getDetalles().add(new DetalleHistoriaClinica());
            model.addAttribute("historia", historia);
            model.addAttribute("pacientes", pacienteService.findAll());
            return "views/historias/form";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar los pacientes: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/guardar")
    public String guardarHistoria(@ModelAttribute("historia") HistoriaClinica historia,
                                  RedirectAttributes redirect,
                                  HttpSession session) {
        try {
            // Recuperar el usuario logueado desde la sesión
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

            if (usuarioLogueado == null) {
                redirect.addFlashAttribute("error", "Debe iniciar sesión antes de crear una historia clínica.");
                return "redirect:/login";
            }

            // Asignar el usuario creador a la historia
            historia.setUsuarioCreador(usuarioLogueado);

            historiaClinicaService.save(historia);
            redirect.addFlashAttribute("success", "Historia clínica guardada correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/historias";
    }


    @GetMapping("/ver/{id}")
    public String verHistoria(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("historia", historiaClinicaService.findById(id));
            return "views/historias/detalle";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/historias";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarHistoria(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            historiaClinicaService.delete(id);
            redirect.addFlashAttribute("success", "Historia clínica eliminada correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/historias";
    }
}