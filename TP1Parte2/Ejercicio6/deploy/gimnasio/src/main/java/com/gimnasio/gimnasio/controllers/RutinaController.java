package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.*;
import com.gimnasio.gimnasio.enumerations.EstadoDetalleRutina;
import com.gimnasio.gimnasio.enumerations.EstadoRutina;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.services.EmpleadoService;
import com.gimnasio.gimnasio.services.RutinaService;
import com.gimnasio.gimnasio.services.SocioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/profesor/rutinas")
public class RutinaController {

    @Autowired
    private RutinaService rutinaService;

    @Autowired
    private SocioService socioService;

    @Autowired
    private EmpleadoService empleadoService;

    private boolean esProfesor(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.PROFESOR;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";

        try {
            List<Rutina> rutinas = rutinaService.listarRutina();
            model.addAttribute("rutinas", rutinas);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "views/profesor/rutinas";
    }
    @GetMapping("/ver/{id}")
    public String verRutina(@PathVariable String id, Model model, HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";

        try {
            Rutina rutina = rutinaService.buscarRutina(id);
            model.addAttribute("rutina", rutina);
            // Apunta a la nueva ubicación
            return "views/ver-rutina";
        } catch (Exception e) {
            return "redirect:/profesor/rutinas?error=" + e.getMessage();
        }
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model, HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";

        try {
            model.addAttribute("socios", socioService.findAllByEliminadoFalse());
            return "views/profesor/rutinas/crear";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar los socios: " + e.getMessage());
            return "views/profesor/rutinas/crear";
        }
    }

    @PostMapping("/crear")
    public String crear(
            @RequestParam String idSocio,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFinalizacion,
            @RequestParam List<String> actividades,
            Model model,
            HttpSession session
    ) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuario == null || usuario.getRol() != RolUsuario.PROFESOR) {
                throw new Exception("Solo un profesor puede crear rutinas");
            }

            // Buscar al profesor asociado al usuario logueado
            Empleado profesor = empleadoService.buscarPorUsuario(usuario);

            Date inicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicio);
            Date fin = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinalizacion);

            List<DetalleRutina> detalles = new ArrayList<>();
            for (String act : actividades) {
                if (act != null && !act.trim().isEmpty()) {
                    DetalleRutina d = new DetalleRutina();
                    d.setActividad(act);
                    d.setEstadoDetalleRutina(EstadoDetalleRutina.SIN_REALIZAR);
                    d.setEliminado(false);
                    d.setFecha(inicio);
                    detalles.add(d);
                }
            }

            rutinaService.crearRutina(profesor.getId(), idSocio, inicio, fin, detalles);
            return "redirect:/profesor/rutinas?success=Rutina creada con éxito";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            try {
                model.addAttribute("socios", socioService.findAllByEliminadoFalse());
            } catch (Exception ex) {
                model.addAttribute("error", "Error al cargar los socios: " + ex.getMessage());
            }
            return "views/profesor/rutinas/crear";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable String id, Model model, HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";

        try {
            Rutina rutina = rutinaService.buscarRutina(id);
            model.addAttribute("rutina", rutina);
            model.addAttribute("socios", socioService.findAllByEliminadoFalse());
            return "views/profesor/rutinas/editar";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/profesor/rutinas/editar";
        }
    }

    @PostMapping("/editar/{id}")
    public String editar(
            @PathVariable String id,
            @RequestParam String idSocio,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFinalizacion,
            @RequestParam List<String> actividades,
            @RequestParam EstadoRutina estado,   // 👈 acá
            Model model,
            HttpSession session
    ) {
        if (!esProfesor(session)) return "redirect:/login";

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        Empleado profesor;
        try {
            profesor = empleadoService.buscarPorUsuario(usuario);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/profesor/rutinas/crear";
        }

        try {
            Date inicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicio);
            Date fin = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinalizacion);

            List<DetalleRutina> detalles = new ArrayList<>();
            for (String act : actividades) {
                if (act != null && !act.trim().isEmpty()) {
                    DetalleRutina d = new DetalleRutina();
                    d.setActividad(act);
                    d.setEstadoDetalleRutina(EstadoDetalleRutina.SIN_REALIZAR);
                    d.setEliminado(false);
                    d.setFecha(inicio);
                    detalles.add(d);
                }
            }

            // Ahora pasás también el estado
            rutinaService.modificarRutina(id, profesor.getId(), idSocio, inicio, fin, detalles, estado);

            return "redirect:/profesor/rutinas?success=Rutina editada con éxito";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            try {
                model.addAttribute("socios", socioService.findAllByEliminadoFalse());
            } catch (Exception ex) {
                model.addAttribute("error", ex.getMessage());
            }
            return "views/profesor/rutinas/editar";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";

        try {
            rutinaService.modificarEstadoRutina(id, EstadoRutina.ANULADA);
            return "redirect:/profesor/rutinas?success=Rutina eliminada correctamente";
        } catch (Exception e) {
            return "redirect:/profesor/rutinas?error=" + e.getMessage();
        }
    }
}
