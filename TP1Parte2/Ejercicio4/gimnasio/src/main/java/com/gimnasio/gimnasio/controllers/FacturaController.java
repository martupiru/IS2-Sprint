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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.OutputStream;
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

    public FacturaController(FacturaService facturaService, CuotaFacturaRepository cuotaFacturaRepository) {
        this.facturaService = facturaService;
        this.cuotaFacturaRepository = cuotaFacturaRepository;
        this.reportService = new ReportService();
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
        params.put("invoiceNumber", factura.getNumeroFactura() != null ? factura.getNumeroFactura().toString() : "");
        // Formatear fecha
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");
        params.put("invoiceDate", factura.getFechaFactura() != null ? df.format(factura.getFechaFactura()) : "");
        params.put("total", factura.getTotalPagado());
        params.put("estado", factura.getEstadoFactura() != null ? factura.getEstadoFactura().toString() : "");

        if (factura.getFormaDePago() != null) {
            String pago = factura.getFormaDePago().getObservacion();
            if (pago == null || pago.isBlank()) {
                pago = factura.getFormaDePago().getTipoPago() != null ? factura.getFormaDePago().getTipoPago().toString() : "";
            }
            params.put("paymentMethod", pago);
        } else {
            params.put("paymentMethod", "");
        }
        String clienteNombre = "Cliente";
        try {
            if (factura.getDetalleFactura() != null && !factura.getDetalleFactura().isEmpty()) {
                DetalleFactura primer = factura.getDetalleFactura().get(0);
                if (primer != null && primer.getCuotaMensual() != null && primer.getCuotaMensual().getSocio() != null) {
                    Object socioObj = primer.getCuotaMensual().getSocio();
                    try {
                        String nombre = (String) socioObj.getClass().getMethod("getNombre").invoke(socioObj);
                        String apellido = (String) socioObj.getClass().getMethod("getApellido").invoke(socioObj);
                        clienteNombre = (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
                        clienteNombre = clienteNombre.trim();
                    } catch (NoSuchMethodException nsme) {
                        // si no tiene esos métodos, fallback a toString()
                        clienteNombre = socioObj.toString();
                    }
                }
            }
        } catch (Exception ex) {
            // No hago nada, queda "Cliente"
        }
        params.put("clientName", clienteNombre);
        List<InvoiceItem> items = new ArrayList<>();
        if (factura.getDetalleFactura() != null) {
            for (DetalleFactura det : factura.getDetalleFactura()) {
                if (det == null) continue;
                CuotaMensual cuota = det.getCuotaMensual();
                if (cuota == null) continue;

                String descripcion = "Cuota " + (cuota.getMes() != null ? cuota.getMes().toString() : "")
                        + " " + (cuota.getAnio() != null ? cuota.getAnio() : "");
                Double precio = 0.0;
                try {
                    precio = cuota.getValorCuota() != null ? cuota.getValorCuota().getValorCuota() : 0.0;
                } catch (Exception ex) {
                    try {
                        precio = (Double) cuota.getClass().getMethod("getValor").invoke(cuota);
                    } catch (Exception e) {
                        precio = 0.0;
                    }
                }
                items.add(new InvoiceItem(descripcion, 1, precio, precio));
            }
        }
        JRDataSource jrDataSource = new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(items);
        // generar PDF
        byte[] pdf = reportService.generarReport("invoice", params, jrDataSource);
        // enviar
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=factura-" + factura.getNumeroFactura() + ".pdf");
        try (OutputStream out = response.getOutputStream()) {
            out.write(pdf);
            out.flush();
        }
    }
}
