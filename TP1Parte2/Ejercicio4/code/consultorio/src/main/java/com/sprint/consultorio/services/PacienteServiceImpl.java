package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.Paciente;
import com.sprint.consultorio.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl extends BaseServiceImpl<Paciente, Long> implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    // validaciones para paciente
    @Override
    protected void beforeSave(Paciente paciente) throws Exception {
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            throw new Exception("El campo 'nombre' es obligatorio para el paciente.");
        }

        if (paciente.getApellido() == null || paciente.getApellido().trim().isEmpty()) {
            throw new Exception("El campo 'apellido' es obligatorio para el paciente.");
        }

        if (paciente.getDocumento() == null || paciente.getDocumento().trim().isEmpty()) {
            throw new Exception("El campo 'documento' es obligatorio para el paciente.");
        }

        Optional<Paciente> existente = pacienteRepository.findAll().stream()
                .filter(p -> p.getDocumento().equals(paciente.getDocumento()))
                .findFirst();

        if (existente.isPresent() && (paciente.getId() == null || !existente.get().getId().equals(paciente.getId()))) {
            throw new Exception("Ya existe un paciente con el documento " + paciente.getDocumento());
        }
    }

    protected void afterSave(Paciente paciente) throws Exception {
       // Generar una foto por defecto si no tiene:
        if (paciente.getFoto() == null) {
            // Se podria crear un FotoPaciente con imagen por defecto
            // paciente.setFoto(new FotoPaciente("default.png"));
            // pacienteRepository.save(paciente);
        }
    }

    @Override
    public List<Paciente> findAll() throws Exception {
        try {
            return pacienteRepository.findAllActivos();
        } catch (Exception e) {
            throw new Exception("Error al listar pacientes activos: " + e.getMessage());
        }
    }
}