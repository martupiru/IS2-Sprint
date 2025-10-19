package com.taller.taller.controllers;

import com.taller.taller.entities.Usuario;
import com.taller.taller.services.ErrorServiceException;
import com.taller.taller.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InicioController {

    @Autowired
    private final UsuarioService usuarioService;

    public InicioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("taller", "Bienvenido al Sistema");

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        if (usuario != null) {
            if (usuario.getRol().toString().equals("ADMIN")) {
                return "redirect:/admin/panel";
            }else {
                return "home";
            }
        }else {
            return "home";
        }

    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("taller", "Registro de Usuario");
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("taller", "Login de Usuario");
        return "login";
    }

    @PostMapping("/register")
    public String registro(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String clave,
            @RequestParam String confirmPassword,
            Model model) throws ErrorServiceException {
        try {
            usuarioService.registrar(clave, nombre, email, confirmPassword);
            model.addAttribute("exito", "Registro exitoso. Por favor, inicie sesión.");
            return "login";
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            model.addAttribute("nombre", nombre);
            model.addAttribute("apellido", apellido);
            model.addAttribute("email", email);
            return "register";
        }
    }
}

