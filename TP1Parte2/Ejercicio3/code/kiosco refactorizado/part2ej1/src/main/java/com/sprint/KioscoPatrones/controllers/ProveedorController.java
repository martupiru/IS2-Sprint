package com.sprint.KioscoPatrones.controllers;

import com.sprint.KioscoPatrones.entities.Proveedor;
import com.sprint.KioscoPatrones.services.BaseService;
import com.sprint.KioscoPatrones.services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario/proveedor")
// Paso 1: Heredar de BaseController, especificando la Entidad y el tipo de su ID
public class ProveedorController extends BaseController<Proveedor, String> {

    @Autowired
    private ProveedorService proveedorService;


    @Override
    protected BaseService<Proveedor, String> getService() {
        return this.proveedorService;
    }

    @Override
    protected String getEntityName() {
        return "proveedor";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("proveedor", new Proveedor());
        // La vista se obtiene de la clase base: getFormView()
        return getFormView();
    }

    // Procesa el guardado de un nuevo proveedor
    @PostMapping("/crear")
    public String crearProveedor(HttpSession session,
                                 @RequestParam String nombre,
                                 @RequestParam String apellido,
                                 @RequestParam String telefono,
                                 @RequestParam String correoElectronico,
                                 @RequestParam String cuit,
                                 Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        try {
            // Llama al método específico del servicio de proveedor
            proveedorService.crearProveedorConPersona(nombre, apellido, telefono, correoElectronico, cuit);
            // Redirige a la lista usando el método de la clase base
            return getRedirectListView();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            // Si hay error, vuelve al formulario
            return getFormView();
        }
    }

    // Muestra el formulario para editar un proveedor existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") String id, HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        try {
            // Usa el método findById del servicio base
            Proveedor proveedor = getService().findById(id);
            model.addAttribute("proveedor", proveedor);
            return getFormView();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return getRedirectListView();
        }
    }

    // Procesa la actualización de un proveedor
    @PostMapping("/editar")
    public String editarProveedor(@RequestParam String id,
                                  @RequestParam String nombre,
                                  @RequestParam String apellido,
                                  @RequestParam String telefono,
                                  @RequestParam String correoElectronico,
                                  @RequestParam String cuit,
                                  HttpSession session,
                                  Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }

        Proveedor prov;
        try {
            prov = proveedorService.findById(id);
        } catch (Exception e) {

            return getRedirectListView();
        }

        try {
            // actulizamos datos
            prov.setNombre(nombre);
            prov.setApellido(apellido);
            prov.setTelefono(telefono);
            prov.setCorreoElectronico(correoElectronico);
            prov.setCuit(cuit);

            //guardamos
            proveedorService.update(id, prov);

            return getRedirectListView();
        } catch (Exception e) {
            // si falla, no se modifica nada
            model.addAttribute("error", "Error al actualizar: " + e.getMessage());
            model.addAttribute("proveedor", prov);
            return getFormView();
        }
    }
}