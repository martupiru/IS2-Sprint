package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.entities.Proveedor;
import com.sprint.part2ej1.entities.Usuario;
import com.sprint.part2ej1.services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Listado de proveedores
    @GetMapping("/detalle")
    public String mostrarABMProveedores(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario); // el usuario logueado
        try {
            model.addAttribute("proveedores", proveedorService.listarProveedoresActivos());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "views/usuario/Menu/proveedores/detalle";
    }

    // Formulario nuevo proveedor
    @GetMapping("/crear")
    public String mostrarFormularioCrearProveedor(HttpSession session,Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // Pasamos un objeto Usuario vacío al formulario
        model.addAttribute("proveedor", new Proveedor());
        return "views/usuario/Menu/proveedores/FormProveedor";
    }

    @PostMapping("/crear")
    public String crearProveedor(HttpSession session,
                                 @RequestParam String nombre,
                                 @RequestParam String apellido,
                                 @RequestParam String telefono,
                                 @RequestParam String correoElectronico,
                                 @RequestParam String cuit,
                                 Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        try {
            proveedorService.crearProveedorConPersona(nombre, apellido, telefono, correoElectronico, cuit);
            return "redirect:/usuario/proveedor/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/usuario/Menu/proveedores/FormProveedor";
        }
    }

    // Formulario editar
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProveedor(@PathVariable("id") String idProveedor, HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        try {
            Proveedor proveedor = proveedorService.buscarProveedor(idProveedor);
            model.addAttribute("proveedor", proveedor);
            return "views/usuario/Menu/proveedores/FormProveedor";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/proveedor/detalle";
        }
    }

    @PostMapping("/editar")
    public String editarProveedor(@RequestParam String id,
                                  @RequestParam String nombre,
                                  @RequestParam String apellido,
                                  @RequestParam String telefono,
                                  @RequestParam String correoElectronico,
                                  @RequestParam String cuit,
                                  HttpSession session,
                                  Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        try {
            Proveedor prov = proveedorService.buscarProveedor(id);

            prov.setNombre(nombre);
            prov.setApellido(apellido);
            prov.setTelefono(telefono);
            prov.setCorreoElectronico(correoElectronico);
            prov.setCuit(cuit);

            proveedorService.modificarProveedor(prov.getId(), prov.getCuit());
            return "redirect:/usuario/proveedor/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/usuario/Menu/proveedores/FormProveedor";
        }
    }

    // Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable("id") String idProveedor, HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        try {
            proveedorService.eliminarProveedor(idProveedor);
            return "redirect:/usuario/proveedor/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/usuario/proveedor/detalle";
        }
    }
}

