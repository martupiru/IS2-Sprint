package com.sprint.consultorio.controllers;

import com.sprint.consultorio.entities.Usuario;
import com.sprint.consultorio.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController implements BaseController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        try {
            model.addAttribute("usuarios", usuarioService.findAll());
            return "views/usuarios/lista";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "views/usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirect) {
        try {
            usuarioService.save(usuario);
            redirect.addFlashAttribute("success", "Usuario guardado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("usuario", usuarioService.findById(id));
            return "views/usuarios/form";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/usuarios";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirect) {
        try {
            usuarioService.update(id, usuario);
            redirect.addFlashAttribute("success", "Usuario actualizado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            usuarioService.delete(id);
            redirect.addFlashAttribute("success", "Usuario eliminado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }
}
