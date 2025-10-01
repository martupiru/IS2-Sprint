package com.sprint.part2ej1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KioscoController {


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
}
