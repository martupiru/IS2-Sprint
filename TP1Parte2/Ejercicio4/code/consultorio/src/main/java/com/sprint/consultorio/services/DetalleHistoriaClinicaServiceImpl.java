package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.DetalleHistoriaClinica;
import com.sprint.consultorio.repositories.DetalleHistoriaClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleHistoriaClinicaServiceImpl
        extends BaseServiceImpl<DetalleHistoriaClinica, Long>
        implements DetalleHistoriaClinicaService {

    @Autowired
    private DetalleHistoriaClinicaRepository detalleHistoriaClinicaRepository;

    @Override
    protected void beforeSave(DetalleHistoriaClinica detalle) throws Exception {
        if (detalle.getDetalleHistoria() == null || detalle.getDetalleHistoria().trim().isEmpty()) {
            throw new Exception("El detalle de la historia no puede estar vacío.");
        }

        if (detalle.getFechaHistoria() == null) {
            throw new Exception("Debe especificarse la fecha del detalle.");
        }
    }

    @Override
    public List<DetalleHistoriaClinica> findAll() throws Exception {
        try {
            return detalleHistoriaClinicaRepository.findAllActivos();
        } catch (Exception e) {
            throw new Exception("Error al listar detalles activos: " + e.getMessage());
        }
    }
}
