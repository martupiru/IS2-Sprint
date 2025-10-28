package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


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

    @GetMapping("/casa")
    public String casa(HttpSession session, Model model) {
        try {
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null) {
                return "redirect:/login";
            }

            switch (usuarioLogueado.getRol()) {
                case ADMINISTRATIVO:
                    return "redirect:/admin/dashboard";
                case PROFESOR:
                    return "redirect:/profesor/dashboard";
                case SOCIO:
                    return "redirect:/socio/dashboard";
                default:
                    return "redirect:/login";
            }
        } catch (Exception e) {
            return "error";
        }
    }

}