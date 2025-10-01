package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.entities.Usuario;
import com.sprint.part2ej1.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "views/usuario/dashboard";
    }


    @GetMapping("/usuario/CRUDusuario")
    public String mostrarABMUsuarios(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        try {
            model.addAttribute("usuarios", usuarioService.listarUsuariosActivos());
        }catch (Exception e){
            return "error";
        }

        return "views/usuario/Menu/usuarios/ABMusuarios";
    }

    // Cambiar Clave
    @GetMapping("/usuario/cclave")
    public String mostrarCambiarClave(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "views/usuario/Menu/usuarios/CambiarClave";
    }

}
