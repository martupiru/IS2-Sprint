package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.entities.Persona;
import com.sprint.part2ej1.entities.Usuario;
import com.sprint.part2ej1.services.PersonaService;
import com.sprint.part2ej1.services.UsuarioService;
import com.sprint.part2ej1.utils.HashForLogin;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/usuario/editar/{id}")
    public String mostrarFormularioEditarUsuario(@PathVariable("id") String idUsuario, HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        try {
            Usuario usuarioEditar = usuarioService.buscarUsuario(idUsuario);
            model.addAttribute("usuario", usuarioEditar);
            return "views/usuario/Menu/usuarios/FormUsuario"; // reutilizamos el mismo formulario
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/usuario/detalle";
        }
    }

    // Guardar cambios del usuario editado
    @PostMapping("/usuario/editar")
    public String editarUsuario(
            @RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String telefono,
            @RequestParam String correoElectronico,
            @RequestParam String cuenta,
            HttpSession session,
            Model model
    ) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        try {
            // Buscar el usuario existente
            Usuario user = usuarioService.buscarUsuario(id);

            // Actualizar solo los campos permitidos
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setTelefono(telefono);
            user.setCorreoElectronico(correoElectronico);
            user.setCuenta(cuenta);

            //se guardan cambios
            usuarioService.modificarUsuario(user.getId(), user.getCuenta(), user.getClave());

            return "redirect:/usuario/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuarioLogueado);
            return "views/usuario/Menu/usuarios/FormUsuario";
        }
    }

    @GetMapping("/usuario/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") String idUsuario, HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        try {
            usuarioService.eliminarUsuario(idUsuario);
            return "redirect:/usuario/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/usuario/Menu/usuarios/detalle";
        }
    }


    @GetMapping("/usuario/clave")
    public String mostrarCambiarClave(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "views/usuario/Menu/usuarios/CambiarClave";
    }

    @PostMapping("/usuario/clave")
    public String cambiarClave(
            @RequestParam String passwordActual,
            @RequestParam String passwordNueva,
            @RequestParam String passwordConfirm,
            HttpSession session,
            Model model
    ) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        try {
            // Verificar que la contraseña actual sea correcta
            if (!HashForLogin.hashClave(passwordActual).equals(usuario.getClave())) {
                model.addAttribute("error", "La clave actual es incorrecta");
                model.addAttribute("usuario", usuario);
                return "views/usuario/Menu/usuarios/CambiarClave";
            }

            // Verificar que la nueva clave sea distinta
            if (passwordActual.equals(passwordNueva)) {
                model.addAttribute("error", "La nueva clave debe ser distinta a la actual");
                model.addAttribute("usuario", usuario);
                return "views/usuario/Menu/usuarios/CambiarClave";
            }

            // Verificar que la confirmación coincida
            if (!passwordNueva.equals(passwordConfirm)) {
                model.addAttribute("error", "La confirmación no coincide con la nueva clave");
                model.addAttribute("usuario", usuario);
                return "views/usuario/Menu/usuarios/CambiarClave";
            }

            // Guardar nueva clave
            usuarioService.modificarClave(usuario.getId(), passwordNueva);

            model.addAttribute("success", "Clave actualizada correctamente");
            model.addAttribute("usuario", usuario);
            return "views/usuario/Menu/usuarios/CambiarClave";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario);
            return "views/usuario/Menu/usuarios/CambiarClave";
        }
    }

}
