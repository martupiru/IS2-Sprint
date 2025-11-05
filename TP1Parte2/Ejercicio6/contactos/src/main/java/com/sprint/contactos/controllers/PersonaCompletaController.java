package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.Empresa;
import com.sprint.contactos.services.AgendaFacade;
import com.sprint.contactos.services.EmpresaService;
import com.sprint.contactos.services.ErrorServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/persona-completa")
public class PersonaCompletaController {

    @Autowired
    private AgendaFacade agendaFacade;

    @Autowired
    private EmpresaService empresaService;

    // Mostrar formulario
    @GetMapping("/alta")
    public String mostrarFormulario(Model model) {
        try {
            model.addAttribute("empresas", empresaService.listarActivos());
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
        }
        return "view/ePersonaCompleta";
    }

    @PostMapping("/guardar")
    public String guardarPersonaCompleta(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String empresaId,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam String tipoTelefono,
            @RequestParam String tipoContacto,
            RedirectAttributes attr) {

        try {
            Empresa empresa = empresaService.obtener(empresaId)
                    .orElseThrow(() -> new ErrorServiceException("Empresa no encontrada"));
            agendaFacade.registrarPersonaCompleta(
                    nombre,
                    apellido,
                    empresa,
                    email,
                    telefono,
                    tipoTelefono,
                    tipoContacto
            );
            attr.addFlashAttribute("msgExito", "Persona registrada exitosamente con la fachada.");
        } catch (Exception e) {
            attr.addFlashAttribute("msgError", "Error: " + e.getMessage());
        }
        return "redirect:/persona/listar";
    }

}
