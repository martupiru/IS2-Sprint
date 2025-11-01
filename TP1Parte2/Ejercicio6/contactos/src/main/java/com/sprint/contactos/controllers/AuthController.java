package com.sprint.contactos.controllers;

import com.sprint.contactos.services.ErrorServiceException;
import com.sprint.contactos.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/register")
    public String mostrarFormularioRegistro() {
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(
            @RequestParam String nombre,
            @RequestParam String clave,
            Model model
    ) {
        try {
            usuarioService.registrar(nombre, clave);
            model.addAttribute("msgExito", "Usuario registrado exitosamente. Ya podés iniciar sesión.");
            return "login";
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
}

