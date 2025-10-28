package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.FotoPaciente;
import com.sprint.consultorio.entities.Paciente;
import com.sprint.consultorio.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl extends BaseServiceImpl<Paciente, Long> implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    // validaciones para paciente
    @Override
    protected void beforeSave(Paciente paciente) throws Exception {
        if (paciente.getNombre() == null || !paciente.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            throw new Exception("El nombre solo puede contener letras y espacios.");
        }

        if (paciente.getApellido() == null || !paciente.getApellido().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            throw new Exception("El apellido solo puede contener letras y espacios.");
        }

        if (paciente.getDocumento() == null || !paciente.getDocumento().matches("^[0-9]+$")) {
            throw new Exception("El documento solo puede contener números.");
        }

        Optional<Paciente> existente = pacienteRepository.findAll().stream()
                .filter(p -> p.getDocumento().equals(paciente.getDocumento()))
                .findFirst();

        if (existente.isPresent() && (paciente.getId() == null || !existente.get().getId().equals(paciente.getId()))) {
            throw new Exception("Ya existe un paciente con el documento " + paciente.getDocumento());
        }

        agregarFotoPorDefecto(paciente);
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

    // 🔹 FOTO POR DEFECTO
    private void agregarFotoPorDefecto(Paciente paciente) {
        try {
            if (paciente.getFoto() == null || paciente.getFoto().getContenido() == null) {
                InputStream defaultImage = getClass().getResourceAsStream("/static/images/default-paciente.jpg");
                if (defaultImage == null) {
                    System.err.println("⚠️ Imagen por defecto no encontrada en /images/default-paciente.jpg");
                    return;
                }

                FotoPaciente fotoDefault = FotoPaciente.builder()
                        .nombre("default-paciente.jpg")
                        .mime("image/jpeg")
                        .contenido(defaultImage.readAllBytes())
                        .build();
                paciente.setFoto(fotoDefault);
            }
        } catch (IOException e) {
            System.err.println("⚠️ Error al cargar la imagen por defecto: " + e.getMessage());
        }
    }
}