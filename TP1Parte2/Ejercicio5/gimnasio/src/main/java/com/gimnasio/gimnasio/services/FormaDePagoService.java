package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.FormaDePago;
import com.gimnasio.gimnasio.enumerations.TipoPago;
import com.gimnasio.gimnasio.repositories.FormaDePagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormaDePagoService {

    @Autowired
    private FormaDePagoRepository formaDePagoRepository;

    public FormaDePago crearFormaDePago(TipoPago tipoPago, String observacion) throws Exception{
        try {
            FormaDePago formaPago = new FormaDePago();
            formaPago.setTipoPago(tipoPago);
            formaPago.setObservacion(observacion);
            formaPago.setEliminado(false);
            formaDePagoRepository.save(formaPago);
            return formaPago;
        } catch (Exception e) {
            throw new Exception("Error al crear la forma de pago: " + e.getMessage());
        }
    }


    public void modificarFormaDePago(String id, TipoPago tipoPago, String observacion) throws Exception{
        try {
            Optional<FormaDePago> formaPago = formaDePagoRepository.findById(id);
            if (formaPago.isPresent()) {
                FormaDePago formaPagoActual = formaPago.get();
                formaPagoActual.setTipoPago(tipoPago);
                formaPagoActual.setObservacion(observacion);
                formaDePagoRepository.save(formaPagoActual);
            } else {
                throw new Exception("Forma de pago no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar la forma de pago: " + e.getMessage());
        }
    }

    public void eliminarFormaDePago(String id) throws Exception{
        try {
            Optional<FormaDePago> formaPago = formaDePagoRepository.findById(id);
            if (formaPago.isPresent()) {
                FormaDePago formaPagoActual = formaPago.get();
                formaPagoActual.setEliminado(true);
                formaDePagoRepository.save(formaPagoActual);
            } else {
                throw new Exception("Forma de pago no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar la forma de pago: " + e.getMessage());
        }
    }

}
