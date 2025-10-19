package com.taller.taller.controllers;

import com.taller.taller.entities.Vehiculo;
import com.taller.taller.services.VehiculoService;
import com.taller.taller.services.ClienteService;
import com.taller.taller.services.ErrorServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vehiculo")
public class VehiculoController extends BaseController<Vehiculo, String> {

    private final ClienteService clienteService;

    public VehiculoController(VehiculoService service, ClienteService clienteService) {
        super(service);
        this.clienteService = clienteService;
        initController(new Vehiculo(), "Listado de Vehículos", "Editar Vehículo");
    }

    @Override
    protected void preAlta() throws ErrorServiceException {
        this.model.addAttribute("clientes", clienteService.listarActivos());
    }

    @Override
    protected void preModificacion() throws ErrorServiceException {
        this.model.addAttribute("clientes", clienteService.listarActivos());
    }
}
