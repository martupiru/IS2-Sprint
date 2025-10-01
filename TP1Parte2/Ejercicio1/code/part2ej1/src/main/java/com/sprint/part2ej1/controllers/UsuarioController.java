package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.entities.Persona;
import com.sprint.part2ej1.entities.Usuario;
import com.sprint.part2ej1.services.PersonaService;
import com.sprint.part2ej1.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PersonaService personaService;

    @GetMapping("/usuario/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "views/usuario/dashboard";
    }


    @GetMapping("/usuario/detalle")
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

        return "views/usuario/Menu/usuarios/detalle";
    }


    @GetMapping("/usuario/crear")
    public String mostrarFormularioCrearUsuario(HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // Pasamos un objeto Usuario vacío al formulario
        model.addAttribute("usuario", new Usuario());
        return "views/usuario/Menu/usuarios/FormUsuario";
    }

    @PostMapping("/usuario/crear")
    public String crearUsuario(
            HttpSession session,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String telefono,
            @RequestParam String correoElectronico,
            @RequestParam String cuenta,
            @RequestParam String clave,
            Model model
    ) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        try {

            usuarioService.crearUsuarioConPersona(nombre, apellido, telefono, correoElectronico, cuenta, clave);

            return "redirect:/usuario/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuarioLogueado);
            return "views/usuario/Menu/usuarios/usuario_form";
        }
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
