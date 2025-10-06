package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.services.MigracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class MigracionController {

    @Autowired
    MigracionService migracionService;

    @GetMapping("/migracion")
    public String formMigracion() {
        return "views/usuario/Menu/migracion/migProveedores";
    }

    @PostMapping("/migracion/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "Por favor, seleccione un archivo para subir.");
            return "redirect:/usuario/migracion";
        }

        try {
            int count = migracionService.migrarProveedores(file);
            redirectAttributes.addFlashAttribute("msg", "¡Migración exitosa! " + count + " proveedores han sido creados.");
        } catch (Exception e) {
            // Manejo de errores durante la migración (ej. formato incorrecto, errores de DB)
            redirectAttributes.addFlashAttribute("msg", "Error durante la migración: " + e.getMessage());
        }

        return "redirect:/usuario/migracion";
    }

}
