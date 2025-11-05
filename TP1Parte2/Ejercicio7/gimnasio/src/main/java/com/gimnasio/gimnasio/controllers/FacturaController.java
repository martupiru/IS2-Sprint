package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Factura;
import com.gimnasio.gimnasio.repositories.CuotaFacturaRepository;
import com.gimnasio.gimnasio.services.FacturaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.OutputStream;

@Controller
public class FacturaController {

    private final FacturaService facturaService;
    private final CuotaFacturaRepository cuotaFacturaRepository;

    public FacturaController(FacturaService facturaService, CuotaFacturaRepository cuotaFacturaRepository) {
        this.facturaService = facturaService;
        this.cuotaFacturaRepository = cuotaFacturaRepository;
    }

    @GetMapping("/socio/factura/{id}")
    public void verFactura(@PathVariable String id, HttpServletResponse response) throws Exception {

        //con el repo busco la factura
        String idFactura = cuotaFacturaRepository.findFacturaIdByCuotaId(id);
        Factura factura = facturaService.buscarFactura(idFactura);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=factura.pdf");

        try (OutputStream out = response.getOutputStream()) {
            // Aquí se genera el PDF simple con los detalles de la factura
            // Ejemplo usando iText:
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, out);
            document.open();
            document.add(new com.itextpdf.text.Paragraph("Factura ID: " + factura.getId()));
            document.add(new com.itextpdf.text.Paragraph("Cliente: " + factura.getNumeroFactura()));
            document.add(new com.itextpdf.text.Paragraph("Fecha: " + factura.getFechaFactura()));
            document.add(new com.itextpdf.text.Paragraph("Total: " + factura.getTotalPagado()));
            document.add(new com.itextpdf.text.Paragraph("Estado: " + factura.getEstadoFactura()));

            document.close();
        }
    }
}
