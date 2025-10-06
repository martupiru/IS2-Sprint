package com.sprint.part2ej1.controllers;


import com.sprint.part2ej1.models.dolar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dolar")
public class DolarController {

    @Autowired
    private com.sprint.part2ej1.services.DolarService dolarService;

    @GetMapping
    public List<dolar> getDolars() {
        return dolarService.obtenerDatosDolar();
    }

    @GetMapping("/compra-venta-fecha")
    public List<String> getCompraVentaFecha() {
        return dolarService.obtenerCompraVentaFecha();
    }

}
