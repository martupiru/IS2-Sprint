package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.HistoriaClinica;
import com.sprint.consultorio.entities.Paciente;
import com.sprint.consultorio.repositories.HistoriaClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoriaClinicaServiceImpl extends BaseServiceImpl<HistoriaClinica, Long> implements HistoriaClinicaService {

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Override
    protected void beforeSave(HistoriaClinica historia) throws Exception {
        if (historia.getPaciente() == null) {
            throw new Exception("La historia clínica debe estar asociada a un paciente.");
        }

        if (historia.getUsuarioCreador() == null) {
            throw new Exception("Debe especificarse el usuario que crea la historia clínica.");
        }

        if (historia.getDetalles() != null) {
            for (var detalle : historia.getDetalles()) {
                if (detalle.getDetalleHistoria() == null || detalle.getDetalleHistoria().trim().isEmpty()) {
                    throw new Exception("Cada detalle debe tener un texto de descripción.");
                }
                if (detalle.getFechaHistoria() == null) {
                    throw new Exception("Cada detalle debe tener una fecha de historia médica.");
                }
            }
        }

    }

    @Override
    protected void beforeUpdate(HistoriaClinica historia) throws Exception {
        if (historia.getPaciente() == null) {
            throw new Exception("La historia clínica debe estar asociada a un paciente.");
        }

        if (historia.getUsuarioCreador() == null) {
            throw new Exception("Debe especificarse el usuario que crea la historia clínica.");
        }

        if (historia.getDetalles() != null) {
            for (var detalle : historia.getDetalles()) {
                if (detalle.getDetalleHistoria() == null || detalle.getDetalleHistoria().trim().isEmpty()) {
                    throw new Exception("Cada detalle debe tener un texto de descripción.");
                }
                if (detalle.getFechaHistoria() == null) {
                    throw new Exception("Cada detalle debe tener una fecha de historia médica.");
                }
            }
        }

    }
}
