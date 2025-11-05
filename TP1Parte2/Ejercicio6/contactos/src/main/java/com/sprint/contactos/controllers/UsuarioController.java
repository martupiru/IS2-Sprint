package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.Usuario;
import com.sprint.contactos.services.ErrorServiceException;
import com.sprint.contactos.services.UsuarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController extends BaseController<Usuario, String> {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService service) {
        super(service);
        this.usuarioService = service;
        initController(
                new Usuario(),
                "Listado de Usuarios",
                "Usuario"
        );
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public String listar(Model model) {
        try {
            List<Usuario> usuarios = usuarioService.listarActivos();
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("titleList", "Listado de Usuarios");
            return "view/lUsuario";
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return "view/lUsuario";
        }
    }

    // ✅ Cambiar rol del usuario (solo admin)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cambiar-rol/{id}")
    public String cambiarRol(@PathVariable String id, RedirectAttributes attr) {
        try {
            usuarioService.cambiarRol(id);
            attr.addFlashAttribute("msgExito", "Rol actualizado correctamente.");
        } catch (ErrorServiceException e) {
            attr.addFlashAttribute("msgError", e.getMessage());
        }
        return "redirect:/usuario/listar";
    }
}
