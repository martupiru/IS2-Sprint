package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.services.PDF;
import com.sprint.part2ej1.services.EXCEL;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class documentosController {

    @Autowired
    private PDF pdfService;

    @GetMapping("/generar-pdf")
    public void generarPdf(HttpServletResponse response) {
        try {

            pdfService.generateDocument(response.getOutputStream());
            response.getOutputStream().flush();

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    private EXCEL excelService;

    @GetMapping("/generar-excel.xlsx")
    public void generarExcel(HttpServletResponse response) {
        try {

            excelService.generateExcel(response.getOutputStream());

            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
