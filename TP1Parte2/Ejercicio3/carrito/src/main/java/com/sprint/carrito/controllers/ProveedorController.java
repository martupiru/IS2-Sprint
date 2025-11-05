package com.sprint.carrito.controllers;

import com.sprint.carrito.entities.Proveedor;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController extends BaseController<Proveedor, String> {

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        super(proveedorService);
        this.proveedorService = proveedorService;
        initController(new Proveedor(), "Gestión de Proveedores", "Editar Proveedor");
    }

    @GetMapping
    public String listarProveedores(Model model, HttpSession session) throws ErrorServiceException {
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login?error=Debe iniciar sesión para ver los proveedores";
        }

        List<Proveedor> proveedores = proveedorService.listarActivos();
        model.addAttribute("entidades", proveedores);
        model.addAttribute("usuario", usuario);
        return "gestion-proveedores";
    }

    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model, HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login?error=Debe iniciar sesión";
        }

        model.addAttribute("entidad", new Proveedor());
        model.addAttribute("tituloForm", "Nuevo Proveedor");
        model.addAttribute("accionForm", "/proveedores/guardar");
        model.addAttribute("usuario", usuario);

        return "form-proveedor";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@ModelAttribute("entidad") Proveedor proveedor,
                                   HttpSession session,
                                   Model model) {
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login?error=Debe iniciar sesión";
        }

        try {
            if (proveedor.getId() != null && proveedor.getId().trim().isEmpty()) {
                proveedor.setId(null);
            }

            proveedorService.alta(proveedor);
            return "redirect:/proveedores?success=Proveedor guardado con éxito";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("entidad", proveedor);
            model.addAttribute("tituloForm", "Nuevo Proveedor");
            model.addAttribute("accionForm", "/proveedores/guardar");
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            return "form-proveedor";
        }
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificacion(@PathVariable String id, Model model, HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login?error=Debe iniciar sesión";
        }

        try {
            Proveedor proveedor = proveedorService.obtener(id)
                    .orElseThrow(() -> new Exception("Proveedor no encontrado"));

            model.addAttribute("entidad", proveedor);
            model.addAttribute("tituloForm", "Editar Proveedor");
            model.addAttribute("accionForm", "/proveedores/modificar");
            model.addAttribute("usuario", usuario);

            return "form-proveedor";
        } catch (Exception e) {
            return "redirect:/proveedores?error=" + e.getMessage();
        }
    }

    @PostMapping("/modificar")
    public String modificarProveedor(@ModelAttribute("entidad") Proveedor proveedor,
                                     HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login?error=Debe iniciar sesión";
        }

        try {
            proveedorService.modificar(proveedor.getId(), proveedor);
            return "redirect:/proveedores?success=Proveedor modificado con éxito";
        } catch (Exception e) {
            return "redirect:/proveedores?error=" + e.getMessage();
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable String id, HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login?error=Debe iniciar sesión";
        }

        try {
            proveedorService.bajaLogica(id);
            return "redirect:/proveedores?success=Proveedor eliminado con éxito";
        } catch (Exception e) {
            return "redirect:/proveedores?error=" + e.getMessage();
        }
    }
}