package com.sprint.contactos.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication auth, Model model) {
        // Si el usuario no está logueado → redirigimos al login
        if (auth == null) {
            return "redirect:/login";
        }

        // Si está autenticado → mostramos el home.html
        model.addAttribute("usuario", auth.getName());
        return "home"; // Este debe estar en templates/home.html
    }


}

