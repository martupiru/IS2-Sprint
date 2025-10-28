package com.sprint.consultorio.controllers;

import com.sprint.consultorio.entities.FotoPaciente;
import com.sprint.consultorio.entities.Paciente;
import com.sprint.consultorio.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
            e.printStackTrace(); // para ver el error real en consola
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/nuevo")
    public String nuevoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "views/pacientes/form";
    }

    // ✅ Guardar paciente (con foto)
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute("paciente") Paciente paciente,
            @RequestParam("archivoFoto") MultipartFile archivoFoto,
            RedirectAttributes redirect) {
        try {
            if (!archivoFoto.isEmpty()) {
                FotoPaciente foto = FotoPaciente.builder()
                        .nombre(archivoFoto.getOriginalFilename())
                        .mime(archivoFoto.getContentType())
                        .contenido(archivoFoto.getBytes())
                        .build();
                paciente.setFoto(foto);
            }
            pacienteService.save(paciente);
            redirect.addFlashAttribute("success", "Paciente guardado correctamente");
        } catch (Exception e) {
            e.printStackTrace(); // para ver el error real en consola
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pacientes";
    }

    // ✅ Mostrar foto en navegador
    @GetMapping("/foto/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> verFoto(@PathVariable Long id) {
        try {
            Paciente paciente = pacienteService.findById(id);
            if (paciente != null && paciente.getFoto() != null) {
                FotoPaciente foto = paciente.getFoto();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, foto.getMime())
                        .body(foto.getContenido());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace(); // para ver el error real en consola
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/editar/{id}")
    public String editarPaciente(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("paciente", pacienteService.findById(id));
            return "views/pacientes/form";
        } catch (Exception e) {
            e.printStackTrace(); // para ver el error real en consola
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/pacientes";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarPaciente(
            @PathVariable Long id,
            @ModelAttribute("paciente") Paciente paciente,
            @RequestParam("archivoFoto") MultipartFile archivoFoto,
            RedirectAttributes redirect) {
        try {
            Paciente existente = pacienteService.findById(id);

            // Si se cargó una nueva foto, reemplazarla
            if (!archivoFoto.isEmpty()) {
                FotoPaciente nuevaFoto = FotoPaciente.builder()
                        .nombre(archivoFoto.getOriginalFilename())
                        .mime(archivoFoto.getContentType())
                        .contenido(archivoFoto.getBytes())
                        .build();
                paciente.setFoto(nuevaFoto);
            } else {
                // Mantener la foto anterior
                paciente.setFoto(existente.getFoto());
            }

            pacienteService.update(id, paciente);
            redirect.addFlashAttribute("success", "Paciente actualizado correctamente");
        } catch (Exception e) {
            e.printStackTrace(); // para ver el error real en consola
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
            e.printStackTrace(); // para ver el error real en consola
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/pacientes";
    }
}