package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.services.PDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class documentosController {

    @Autowired
    private PDF pdfService;

    @GetMapping("/generar-pdf")
    public String documentosController() {
        // Lógica para generar el PDF
        try {
            pdfService.generateDocument();
            return "redirect:/redirect:/usuario/dashboard"; // Redirige a una página de éxito
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Redirige a una página de error
        }
    }
}
