package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.entities.Direccion;
import com.sprint.part2ej1.entities.Usuario;
import com.sprint.part2ej1.services.DireccionService;
import com.sprint.part2ej1.services.LocalidadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DireccionController {


    @Autowired
    private DireccionService direccionService;

    @Autowired
    private LocalidadService localidadService;

    @GetMapping("/direccion/googlemaps/{id}")
    public String verDireccion(@PathVariable String id, Model model) throws Exception {
        Direccion direccion = direccionService.buscarDireccion(id);
        String url = direccionService.getGoogleMapsUrl(direccion);

        model.addAttribute("direccion", direccion);
        model.addAttribute("googleMapsUrl", url);
        return "views/usuario/Menu/direccion/detalle";
    }

    @GetMapping("/usuario/direccion/direccion-empresa")
    public String redirigirADireccionEmpresa() {
        return "redirect:https://www.google.com/maps?q=-32.88970575178735,-68.84457510855037";
    }


    // ABM de Direccion
    @GetMapping("/usuario/direccion/FormDireccion")
    public String mostrarFormDireccion(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        try {
            model.addAttribute("direccion", new Direccion());
            model.addAttribute("localidades", localidadService.listarLocalidadesActivas());
        } catch (Exception e) {
            return "error";
        }

        return "views/usuario/Menu/direccion/FormDireccion";
    }


        @GetMapping("/usuario/direccion/crear")
        public String mostrarFormularioCrearDireccion(Model model) {
            model.addAttribute("direccion", new Direccion());
            // Suponiendo que necesitas pasar la lista de localidades al formulario
            // model.addAttribute("localidades", localidadService.listarLocalidades());
            return "views/usuario/Menu/direccion/FormDireccion";
        }

        @PostMapping("/usuario/direccion/crear")
        public String crearDireccion(
                @RequestParam String calle,
                @RequestParam String numeracion,
                @RequestParam String barrio,
                @RequestParam String manzanaPiso,
                @RequestParam String casaDepartamento,
                @RequestParam String referencia,
                @RequestParam String idLocalidad,
                Model model
        ) {
            try {
                direccionService.crearDireccion(calle, numeracion, barrio, manzanaPiso, casaDepartamento, referencia, idLocalidad);
                return "redirect:/direccion/lista";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                model.addAttribute("direccion", new Direccion());
                return "views/usuario/Menu/direccion/FormDireccion";
            }
        }

        @GetMapping("/usuario/direccion/editar/{id}")
        public String mostrarFormularioEditarDireccion(@PathVariable("id") String idDireccion, Model model) {
            try {
                Direccion direccionEditar = direccionService.buscarDireccion(idDireccion);
                model.addAttribute("direccion", direccionEditar);
                // model.addAttribute("localidades", localidadService.listarLocalidades());
                return "views/usuario/Menu/direccion/FormDireccion";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "redirect:/direccion/lista";
            }
        }

        @PostMapping("/usuario/direccion/editar")
        public String editarDireccion(
                @RequestParam String id,
                @RequestParam String calle,
                @RequestParam String numeracion,
                @RequestParam String barrio,
                @RequestParam String manzanaPiso,
                @RequestParam String casaDepartamento,
                @RequestParam String referencia,
                @RequestParam String idLocalidad,
                Model model
        ) throws Exception {
            try {
                direccionService.modificarDireccion(id, calle, numeracion, barrio, manzanaPiso, casaDepartamento, referencia, idLocalidad);
                return "redirect:/direccion/lista";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                model.addAttribute("direccion", direccionService.buscarDireccion(id));
                return "views/usuario/Menu/direccion/FormDireccion";
            }
        }

        @GetMapping("/usuario/direccion/eliminar/{id}")
        public String eliminarDireccion(@PathVariable("id") String idDireccion, Model model) {
            try {
                direccionService.eliminarDireccion(idDireccion);
                return "redirect:/direccion/lista";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "views/usuario/Menu/direccion/detalle";
            }
        }

        @GetMapping("/usuario/direccion/lista")
        public String listarDirecciones(Model model) {
            try {
                model.addAttribute("direcciones", direccionService.listarDireccionesActivas());
                return "views/usuario/Menu/direccion/lista";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "views/usuario/Menu/direccion/lista";
            }
        }
}
