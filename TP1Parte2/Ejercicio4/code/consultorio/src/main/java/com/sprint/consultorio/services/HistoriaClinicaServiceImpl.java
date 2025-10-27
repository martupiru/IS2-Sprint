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
            throw new Exception("Debe asociarse un paciente.");
        }

        // Si no hay usuario creador, permitir null (temporal)
        if (historia.getUsuarioCreador() == null) {
            historia.setUsuarioCreador(null);
        }

        // Si no hay detalles, crear uno vacío para evitar error
        if (historia.getDetalles() == null || historia.getDetalles().isEmpty()) {
            throw new Exception("Debe agregarse al menos un detalle a la historia clínica.");
        }

        // VALIDACIÓN: una historia por paciente
        if (historiaClinicaRepository.existsByPaciente_Id(historia.getPaciente().getId())) {
            throw new Exception("El paciente ya posee una historia clínica registrada.");
        }

        // Completar datos faltantes en detalles
        for (var detalle : historia.getDetalles()) {
            if (detalle.getFechaHistoria() == null) {
                detalle.setFechaHistoria(java.time.LocalDate.now());
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
