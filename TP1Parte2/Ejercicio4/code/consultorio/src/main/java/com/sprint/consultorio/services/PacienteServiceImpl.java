package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.Paciente;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl extends BaseServiceImpl<Paciente, Long> implements PacienteService {

    // validaciones para paciente
    @Override
    protected void beforeSave(Paciente paciente) throws Exception {
        if (paciente.getDocumento() == null || paciente.getDocumento().trim().isEmpty()) {
            throw new Exception("El campo 'documento' es obligatorio para el paciente.");
        }
        // añadir mas validaciones
        // no puede haber dni duplicados, etc
    }
}