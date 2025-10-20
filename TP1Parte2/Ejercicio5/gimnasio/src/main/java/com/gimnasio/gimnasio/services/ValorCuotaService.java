package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.ValorCuota;
import com.gimnasio.gimnasio.repositories.ValorCuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ValorCuotaService {

    @Autowired
    private ValorCuotaRepository valorCuotaRepository;

    public List<ValorCuota> listarTodos() {
        return valorCuotaRepository.findAll();
    }

    @Transactional
    public void cambiarValorCuota(double nuevoValor) throws Exception {
        Date hoy = new Date();


        List<ValorCuota> activos = valorCuotaRepository.findByEliminadoFalse();
        for (ValorCuota v : activos) {
            v.setEliminado(true);
            v.setFechaHasta(hoy);
            valorCuotaRepository.save(v);
        }

        // Crear la nueva cuota activa
        ValorCuota nuevaCuota = new ValorCuota();
        nuevaCuota.setValorCuota(nuevoValor);
        nuevaCuota.setFechaDesde(hoy);
        nuevaCuota.setFechaHasta(null);
        nuevaCuota.setEliminado(false);

        valorCuotaRepository.save(nuevaCuota);
    }

    public double obtenerValorActivo() throws Exception {
        ValorCuota valor = valorCuotaRepository.findFirstByEliminadoFalseOrderByFechaDesdeDesc()
                .orElseThrow(() -> new Exception("No hay valor de cuota activo."));
        return valor.getValorCuota();
    }

    public ValorCuota obtenerEntidadActiva() throws Exception {
        return valorCuotaRepository.findFirstByEliminadoFalseOrderByFechaDesdeDesc()
                .orElseThrow(() -> new Exception("No hay valor de cuota activo."));
    }
}
