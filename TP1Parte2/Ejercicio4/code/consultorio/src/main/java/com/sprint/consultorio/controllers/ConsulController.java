package com.sprint.consultorio.controllers;

import com.sprint.consultorio.entities.Usuario;
import com.sprint.consultorio.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ConsulController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/inicio";
    }

    @GetMapping("/inicio")
    public String inicio() {
        try {
            return "views/inicio";
        } catch (Exception e) {
            return "error";
        }
    }
    @GetMapping("/login")
    public String mostrarLogin() {
        try {
            return "views/login";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String nombreUsuario,
                                @RequestParam String clave,
                                Model model,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        Optional<Usuario> usuarioOpt = usuarioService.login(nombreUsuario, clave);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            session.setAttribute("usuarioLogueado", usuario);

            return "redirect:/admin/dashboard";

        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/login";
        }
    }

    @GetMapping("/admin/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "views/admin/dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Has cerrado sesión exitosamente.");
        return "redirect:/login";
    }
}
