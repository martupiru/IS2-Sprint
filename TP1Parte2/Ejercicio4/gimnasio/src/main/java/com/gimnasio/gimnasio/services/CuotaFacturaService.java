package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.CuotaFactura;
import com.gimnasio.gimnasio.repositories.CuotaFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuotaFacturaService {

    @Autowired
    private CuotaFacturaRepository cuotaFacturaRepository;

    public void crearCuotaFactura(String idCuota, String idFactura) throws Exception {
        try {
            CuotaFactura cuotaFactura = new CuotaFactura();
            cuotaFactura.setId_cuota(idCuota);
            cuotaFactura.setId_factura(idFactura);
            cuotaFacturaRepository.save(cuotaFactura);
        } catch (Exception e) {
            throw new Exception("Error al crear la relación cuota-factura: " + e.getMessage());
        }
    }

}
