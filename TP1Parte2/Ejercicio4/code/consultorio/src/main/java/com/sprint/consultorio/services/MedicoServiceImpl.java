package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.Medico;
import com.sprint.consultorio.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicoServiceImpl extends BaseServiceImpl<Medico, Long> implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    protected void beforeSave(Medico medico) throws Exception {
        if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
            throw new Exception("El campo 'nombre' es obligatorio para el médico.");
        }

        if (medico.getApellido() == null || medico.getApellido().trim().isEmpty()) {
            throw new Exception("El campo 'apellido' es obligatorio para el médico.");
        }

        if (medico.getDocumento() == null || medico.getDocumento().trim().isEmpty()) {
            throw new Exception("El campo 'documento' es obligatorio para el médico.");
        }

        Optional<Medico> existente = medicoRepository.findAll().stream()
                .filter(m -> m.getDocumento().equals(medico.getDocumento()))
                .findFirst();

        if (existente.isPresent() && (medico.getId() == null || !existente.get().getId().equals(medico.getId()))) {
            throw new Exception("Ya existe un médico con el documento " + medico.getDocumento());
        }
    }

    @Override
    protected void beforeUpdate(Medico medico) throws Exception {
        if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
            throw new Exception("El campo 'nombre' es obligatorio para el médico.");
        }

        if (medico.getApellido() == null || medico.getApellido().trim().isEmpty()) {
            throw new Exception("El campo 'apellido' es obligatorio para el médico.");
        }

        if (medico.getDocumento() == null || medico.getDocumento().trim().isEmpty()) {
            throw new Exception("El campo 'documento' es obligatorio para el médico.");
        }

        Optional<Medico> existente = medicoRepository.findAll().stream()
                .filter(m -> m.getDocumento().equals(medico.getDocumento()))
                .findFirst();

        if (existente.isPresent() && (medico.getId() == null || !existente.get().getId().equals(medico.getId()))) {
            throw new Exception("Ya existe un médico con el documento " + medico.getDocumento());
        }
    }
}
