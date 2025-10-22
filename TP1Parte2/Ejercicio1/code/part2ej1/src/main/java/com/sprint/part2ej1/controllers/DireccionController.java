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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private LocalidadService localidadService;

    @GetMapping("/direccion/googlemaps/{id}")
    public String verDireccion(@PathVariable String id, Model model) {
        try {
            Direccion direccion = direccionService.buscarDireccion(id);
            String url = direccionService.getGoogleMapsUrl(direccion);
            model.addAttribute("direccion", direccion);
            model.addAttribute("googleMapsUrl", url);
            return "views/usuario/Menu/direccion/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/usuario/Menu/direccion/detalle";
        }
    }

    // Mostrar formulario (con sesión)
    @GetMapping("/usuario/direccion/FormDireccion")
    public String mostrarFormDireccion(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("direccion", new Direccion());
        List localidades;
        try {
            localidades = localidadService.listarLocalidadesActivas();
        } catch (Exception e) {
            localidades = List.of();
        }
        model.addAttribute("localidades", localidades);
        return "views/usuario/Menu/direccion/FormDireccion";
    }

    @GetMapping("/usuario/direccion/crear")
    public String mostrarFormularioCrearDireccion(Model model) {
        model.addAttribute("direccion", new Direccion());
        List localidades;
        try {
            localidades = localidadService.listarLocalidadesActivas();
        } catch (Exception e) {
            localidades = List.of();
        }
        model.addAttribute("localidades", localidades);
        return "views/usuario/Menu/direccion/FormDireccion";
    }

    @PostMapping("/usuario/direccion/crear")
    public String crearDireccion(
            @RequestParam String latitud,
            @RequestParam String longitud,
            @RequestParam String calle,
            @RequestParam String numeracion,
            @RequestParam String barrio,
            @RequestParam String manzanaPiso,
            @RequestParam String casaDepartamento,
            @RequestParam String referencia,
            @RequestParam(required = false) String idLocalidad,
            Model model
    ) {
        try {
            if (idLocalidad == null || idLocalidad.trim().isEmpty()) {
                throw new Exception("La localidad es requerida");
            }
            direccionService.crearDireccion(latitud, longitud, calle, numeracion, barrio, manzanaPiso, casaDepartamento, referencia, idLocalidad);
            return "redirect:/usuario/direccion/lista";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("direccion", new Direccion());
            List localidades;
            try {
                localidades = localidadService.listarLocalidadesActivas();
            } catch (Exception ex) {
                localidades = List.of();
            }
            model.addAttribute("localidades", localidades);
            return "views/usuario/Menu/direccion/FormDireccion";
        }
    }

    @GetMapping("/usuario/direccion/editar/{id}")
    public String mostrarFormularioEditarDireccion(@PathVariable("id") String idDireccion, Model model) {
        try {
            Direccion direccionEditar = direccionService.buscarDireccion(idDireccion);
            model.addAttribute("direccion", direccionEditar);
            List localidades;
            try {
                localidades = localidadService.listarLocalidadesActivas();
            } catch (Exception e) {
                localidades = List.of();
            }
            model.addAttribute("localidades", localidades);
            return "views/usuario/Menu/direccion/FormDireccion";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/usuario/direccion/lista";
        }
    }

    @PostMapping("/usuario/direccion/editar")
    public String editarDireccion(
            @RequestParam String id,
            @RequestParam String latitud,
            @RequestParam String longitud,
            @RequestParam String calle,
            @RequestParam String numeracion,
            @RequestParam String barrio,
            @RequestParam String manzanaPiso,
            @RequestParam String casaDepartamento,
            @RequestParam String referencia,
            @RequestParam(required = false) String idLocalidad,
            Model model
    ) {
        try {
            if (idLocalidad == null || idLocalidad.trim().isEmpty()) {
                throw new Exception("La localidad es requerida");
            }
            direccionService.modificarDireccion(id, latitud, longitud, calle, numeracion, barrio, manzanaPiso, casaDepartamento, referencia, idLocalidad);
            return "redirect:/usuario/direccion/lista";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            try {
                model.addAttribute("direccion", direccionService.buscarDireccion(id));
            } catch (Exception ex) {
                model.addAttribute("direccion", new Direccion());
            }
            List localidades;
            try {
                localidades = localidadService.listarLocalidadesActivas();
            } catch (Exception ex) {
                localidades = List.of();
            }
            model.addAttribute("localidades", localidades);
            return "views/usuario/Menu/direccion/FormDireccion";
        }
    }

    @GetMapping("/usuario/direccion/eliminar/{id}")
    public String eliminarDireccion(@PathVariable("id") String idDireccion, Model model) {
        try {
            direccionService.eliminarDireccion(idDireccion);
            return "redirect:/usuario/direccion/lista";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/usuario/Menu/direccion/detalle";
        }
    }

    @GetMapping("/usuario/direccion/lista")
    public String listarDirecciones(Model model) {
        try {
            List<Direccion> direcciones = direccionService.listarDireccionesActivas();
            model.addAttribute("direcciones", direcciones);
            Map<String, String> googleMapsUrls = new HashMap<>();
            for (Direccion d : direcciones) {
                String url = direccionService.getGoogleMapsUrl(d);
                if (url != null) {
                    googleMapsUrls.put(d.getId(), url);
                }
            }
            model.addAttribute("googleMapsUrls", googleMapsUrls);
            return "views/usuario/Menu/direccion/lista";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("direcciones", List.of());
            model.addAttribute("googleMapsUrls", Map.of());
            return "views/usuario/Menu/direccion/lista";
        }
    }
}
