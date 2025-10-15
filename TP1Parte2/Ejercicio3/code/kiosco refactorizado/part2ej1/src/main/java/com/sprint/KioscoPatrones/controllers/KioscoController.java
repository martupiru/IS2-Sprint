package com.sprint.KioscoPatrones.controllers;

import com.sprint.KioscoPatrones.entities.Usuario;
import com.sprint.KioscoPatrones.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class KioscoController {

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
    public String procesarLogin(@RequestParam String cuenta,
                                @RequestParam String password,
                                Model model,
                                HttpSession session) {

        Optional<Usuario> usuarioOpt = usuarioService.login(cuenta, password);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            session.setAttribute("usuarioLogueado", usuario);

            return "redirect:/usuario/dashboard";

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
