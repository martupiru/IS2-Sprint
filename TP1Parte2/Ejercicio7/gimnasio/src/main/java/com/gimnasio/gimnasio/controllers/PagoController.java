package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.entities.Factura;
import com.gimnasio.gimnasio.entities.FormaDePago;
import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
import com.gimnasio.gimnasio.enumerations.TipoPago;
import com.gimnasio.gimnasio.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class PagoController {

    private final PagoService testMP;
    private final CuotaMensualService cuotaService;
    private final FormaDePagoService formaDePagoService;
    private final FacturaService facturaService;
    private final CuotaFacturaService CuotaFacturaService;

    public PagoController(PagoService testMP, CuotaMensualService cuotaService, FormaDePagoService formaDePagoService, FacturaService facturaService, CuotaFacturaService CuotaFacturaService) {
        this.testMP = testMP;
        this.cuotaService = cuotaService;
        this.formaDePagoService = formaDePagoService;
        this.facturaService = facturaService;
        this.CuotaFacturaService = CuotaFacturaService;
    }

    @GetMapping("/pago")
    public String pagar() throws IOException {
        try {
            //  arma la preference y nos da la URL de pago
            String initPoint = testMP.Pago();

            // caso de exito Redirigimos al usuario a Mercado Pago
            return "redirect:" + initPoint;


        } catch (Exception e) {
            return "redirect:/inicio";
        }
    }
    @GetMapping("/socio/pagar-cuota/{id}")
    public String pagarCuota(@PathVariable String id) throws Exception {
        CuotaMensual cuota = cuotaService.buscarCuotaMensual(id);
        // creo Forma de pago, factura y detalle de factura
        FormaDePago nuevaForma = formaDePagoService.crearFormaDePago(TipoPago.BILLETERA_VIRTUAL, "Pago via Mercado Pago");
        com.gimnasio.gimnasio.entities.DetalleFactura nuevoDetalle = facturaService.crearDetalleFactura(id,cuota);


        // va a recibir un 1 pero no importa porque el numero de factura se genera automaticamente
        Factura factura = facturaService.crearFactura2(1L, new java.util.Date(), cuota.getValorCuota().getValorCuota(), com.gimnasio.gimnasio.enumerations.EstadoFactura.PAGADA, java.util.Collections.singletonList(nuevoDetalle), nuevaForma);

        //crear la relacion cuota-factura
        CuotaFacturaService.crearCuotaFactura(id, factura.getId());

        // Cambiar el estado a PAGADA
        cuotaService.modificarCuotaMensual(id, cuota.getSocio().getId(), cuota.getMes(), cuota.getAnio(), EstadoCuotaMensual.PAGADA, cuota.getFechaVencimiento());

        return "redirect:/pago";
    }

}
