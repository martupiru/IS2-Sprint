package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.entities.Direccion;
import com.sprint.part2ej1.services.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DireccionController {


    @Autowired
    private DireccionService direccionService;

    @GetMapping("/direccion/{id}")
    public String verDireccion(@PathVariable String id, Model model) throws Exception {
        Direccion direccion = direccionService.buscarDireccion(id);
        String url = direccionService.getGoogleMapsUrl(direccion);

        model.addAttribute("direccion", direccion);
        model.addAttribute("googleMapsUrl", url);
        return "views/usuario/Menu/direccion/detalle";
    }
}
