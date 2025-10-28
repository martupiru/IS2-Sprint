package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class inicioController {

    @Autowired
    private final AuthService authService;


    public inicioController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping("/")
    public String mostrarLogin(Model model) {
        model.addAttribute("titulo", "Iniciar Sesión");
        return "login";
    }


    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        boolean success = authService.login(username, password);

        if (success) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }


    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("titulo", "Bienvenido al Sistema");
        return "home";
    }
}
