package com.taller.taller.controllers;

import com.taller.taller.entities.HistorialArreglo;
import com.taller.taller.entities.Vehiculo;
import com.taller.taller.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/historialarreglo")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class HistorialArregloController extends BaseController<HistorialArreglo, String> {

    private final TallerFacade tallerFacade;
    private final VehiculoService vehiculoService;
    private final MecanicoService mecanicoService;

    public HistorialArregloController(HistorialArregloService service, VehiculoService vehiculoService, MecanicoService mecanicoService, TallerFacade tallerFacade) {
        super(service);
        this.vehiculoService = vehiculoService;
        this.mecanicoService = mecanicoService;
        this.tallerFacade = tallerFacade;
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

    @GetMapping("/listByVehiculo")
    public String listarPorVehiculo(@RequestParam String vehiculo, Model model) {
        try {
            Vehiculo v = vehiculoService.obtener(vehiculo)
                    .orElseThrow(() -> new ErrorServiceException("Vehículo no encontrado"));

            List<HistorialArreglo> lista = service.listarActivos().stream()
                    .filter(h -> h.getVehiculo() != null && h.getVehiculo().getId().equals(v.getId()))
                    .toList();

            model.addAttribute("items", lista);
            model.addAttribute("titleList", "Historial de Arreglos del Vehículo " + v.getPatente());
            model.addAttribute("nameEntityLower", nameEntityLower);
        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
        }

        return viewList;
    }

    @PostMapping("/registrarReparacion")
    public String registrarReparacion(
            @RequestParam String idVehiculo,
            @RequestParam String idMecanico,
            @RequestParam String detalle,
            @RequestParam String fechaArreglo,
            RedirectAttributes ra) {

        try {
            tallerFacade.registrarReparacion(idVehiculo, idMecanico, detalle, fechaArreglo);
            ra.addFlashAttribute("msgExito", "Reparación registrada correctamente.");
        } catch (ErrorServiceException e) {
            ra.addFlashAttribute("msgError", e.getMessage());
        } catch (Exception e) {
            ra.addFlashAttribute("msgError", "Error inesperado: " + e.getMessage());
        }

        return "redirect:/historialarreglo/list";
    }
}
