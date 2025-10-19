package com.taller.taller.controllers;

import com.taller.taller.entities.HistorialArreglo;
import com.taller.taller.services.HistorialArregloService;
import com.taller.taller.services.VehiculoService;
import com.taller.taller.services.MecanicoService;
import com.taller.taller.services.ErrorServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/historialarreglo")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class HistorialArregloController extends BaseController<HistorialArreglo, String> {

    private final VehiculoService vehiculoService;
    private final MecanicoService mecanicoService;

    public HistorialArregloController(HistorialArregloService service, VehiculoService vehiculoService, MecanicoService mecanicoService) {
        super(service);
        this.vehiculoService = vehiculoService;
        this.mecanicoService = mecanicoService;
        initController(new HistorialArreglo(), "Historial de Arreglos", "Editar Historial");
    }

    @Override
    protected void preAlta() throws ErrorServiceException {

        this.model.addAttribute("mecanicos", mecanicoService.listarActivos());
        this.model.addAttribute("vehiculos", vehiculoService.listarActivos());
    }

    @Override
    protected void preModificacion() throws ErrorServiceException {

        this.model.addAttribute("mecanicos", mecanicoService.listarActivos());
        this.model.addAttribute("vehiculos", vehiculoService.listarActivos());
    }
}
