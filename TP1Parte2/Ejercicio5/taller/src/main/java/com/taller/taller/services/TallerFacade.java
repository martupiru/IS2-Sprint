package com.taller.taller.services;

import com.taller.taller.entities.Cliente;
import com.taller.taller.entities.HistorialArreglo;
import com.taller.taller.entities.Mecanico;
import com.taller.taller.entities.Vehiculo;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class TallerFacade {

    private final VehiculoService vehiculoService;
    private final ClienteService clienteService;
    private final HistorialArregloService historialService;
    private final MecanicoService mecanicoService;

    public TallerFacade(VehiculoService vehiculoService,
                        ClienteService clienteService,
                        HistorialArregloService historialService,
                        MecanicoService mecanicoService) {
        this.vehiculoService = vehiculoService;
        this.clienteService = clienteService;
        this.historialService = historialService;
        this.mecanicoService = mecanicoService;
    }

    @Transactional
    public void registrarReparacion(String idVehiculo,
                                    String idMecanico,
                                    String detalle,
                                    String fechaArregloStr)
            throws ErrorServiceException {

        // 1) Buscamos las entidades relacionadas
        Vehiculo vehiculo = vehiculoService.obtener(idVehiculo)
                .orElseThrow(() -> new ErrorServiceException("Vehículo no encontrado"));
        Mecanico mecanico = mecanicoService.obtener(idMecanico)
                .orElseThrow(() -> new ErrorServiceException("Mecánico no encontrado"));

        // 2) Creamos el Historial con el Factory Method del service
        HistorialArreglo h = historialService.createEmpty();
        h.setVehiculo(vehiculo);
        h.setMecanico(mecanico);
        h.setDetalleArreglo(detalle);

        // 3) Parsear la fecha enviada desde el formulario
        try {
            // Ejemplo de entrada: "2025-10-29T23:15"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime ldt = LocalDateTime.parse(fechaArregloStr, formatter);

            // Convertimos LocalDateTime -> Date (usando zona horaria del sistema)
            Date fechaComoDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

            h.setFechaArreglo(fechaComoDate);
        } catch (Exception e) {
            // Si hubo problema parseando, usamos "ahora"
            h.setFechaArreglo(new Date());
        }

        // 4) Guardar usando la lógica común del service (Template Method)
        historialService.alta(h);
    }

    /**
     * Caso de uso: obtener el historial completo de un cliente
     */
    public List<HistorialArreglo> obtenerReparacionesCliente(String idCliente)
            throws ErrorServiceException {
        Cliente cliente = clienteService.obtener(idCliente)
                .orElseThrow(() -> new ErrorServiceException("Cliente no encontrado"));

        return historialService.listarActivos().stream()
                .filter(h -> h.getVehiculo() != null
                        && h.getVehiculo().getCliente() != null
                        && h.getVehiculo().getCliente().getId().equals(cliente.getId()))
                .toList();
    }
}
