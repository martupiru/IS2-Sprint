package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PortalController {

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
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                Model model,
                                HttpSession session) {

        Optional<Usuario> usuarioOpt = usuarioService.login(email, password);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            session.setAttribute("usuarioLogueado", usuario);

            switch (usuario.getRol()) {
                case ADMINISTRATIVO:
                    return "redirect:/admin/dashboard";
                case PROFESOR:
                    return "redirect:/profesor/dashboard";
                case SOCIO:
                    return "redirect:/socio/dashboard";
                default:
                    model.addAttribute("error", "Rol desconocido");
                    return "views/login";
            }
        } else {
            model.addAttribute("error", "Email o contraseña incorrectos");
            return "views/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}