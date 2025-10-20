package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Factura;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.repositories.CuotaFacturaRepository;
import com.gimnasio.gimnasio.services.FacturaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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


    private boolean esSocio(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.SOCIO;
    }

    @GetMapping("/socio/factura/{id}")
    public void verFactura(@PathVariable String id, HttpSession session, HttpServletResponse response) throws Exception {
        if (!esSocio(session)) {
            response.sendRedirect("/login");
            return;
        }

        String idFactura = cuotaFacturaRepository.findFacturaIdByCuotaId(id);
        Factura factura = facturaService.buscarFactura(idFactura);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=factura.pdf");

        try (OutputStream out = response.getOutputStream()) {

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
