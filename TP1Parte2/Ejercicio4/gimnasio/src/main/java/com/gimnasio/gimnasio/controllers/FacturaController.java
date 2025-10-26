package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.DTO.InvoiceItem;
import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.entities.DetalleFactura;
import com.gimnasio.gimnasio.entities.Factura;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.repositories.CuotaFacturaRepository;
import com.gimnasio.gimnasio.services.FacturaService;
import com.gimnasio.gimnasio.services.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.sql.DataSource;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FacturaController {

    private final FacturaService facturaService;
    private final CuotaFacturaRepository cuotaFacturaRepository;
    private final ReportService reportService;
    private final DataSource dataSource; //Para conexion a db

    public FacturaController(FacturaService facturaService, CuotaFacturaRepository cuotaFacturaRepository, ReportService reportService, DataSource dataSource) {
        this.facturaService = facturaService;
        this.cuotaFacturaRepository = cuotaFacturaRepository;
        this.reportService = reportService;
        this.dataSource = dataSource;
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
        if (factura == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Factura no encontrada");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("facturaId", idFactura);

        // Obtener conexión a la base de datos
        byte[] pdf;
        try (Connection conn = dataSource.getConnection()) {
            pdf = reportService.generarReport("invoice", params, conn);
        }

        // enviar PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=factura-" + factura.getNumeroFactura() + ".pdf");
        try (OutputStream out = response.getOutputStream()) {
            out.write(pdf);
            out.flush();
        }
    }
}
