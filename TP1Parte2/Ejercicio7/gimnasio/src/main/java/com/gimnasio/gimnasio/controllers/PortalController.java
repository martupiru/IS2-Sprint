package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalController {

    // Si alguien entra a "/" lo mando a /inicio
    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/inicio";
    }

    // Página de login (solo GET, el POST lo maneja Spring Security en /logincheck)
    @GetMapping("/login")
    public String login(
            Model model,
            HttpSession session,
            String error,
            String logout
    ) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("msg", "Has cerrado sesión correctamente");
        }
        return "views/login"; // /templates/views/login.html
    }

    // Punto de entrada después del login
    @GetMapping("/inicio")
    public String inicio(HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        // Si no hay nadie logueado, muestro la página pública de inicio
        if (usuario == null) {
            return "views/inicio";   // /templates/views/inicio.html
        }

        // Si hay usuario logueado, redirijo según el rol
        if (usuario.getRol() == RolUsuario.ADMINISTRATIVO) {
            return "redirect:/admin/dashboard";
        } else if (usuario.getRol() == RolUsuario.PROFESOR) {
            return "redirect:/profesor/dashboard";
        } else if (usuario.getRol() == RolUsuario.SOCIO) {
            return "redirect:/socio/dashboard";
        }

        // Si por alguna razón el rol es desconocido, muestro inicio genérico
        model.addAttribute("usuario", usuario);
        return "views/inicio";
    }
}
