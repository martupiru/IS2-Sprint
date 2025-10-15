package com.sprint.KioscoPatrones.services;

import com.sprint.KioscoPatrones.entities.Persona;
import com.sprint.KioscoPatrones.repositories.PersonaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public void crearPersona(String nombre, String apellido, String telefono, String correoElectronico) throws Exception {
        try {
            validar(nombre, apellido, telefono, correoElectronico);
            Persona persona = new Persona();
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setTelefono(telefono);
            persona.setCorreoElectronico(correoElectronico);
            persona.setEliminado(false);
            personaRepository.save(persona);
        } catch (Exception e) {
            throw new Exception("Error al crear persona: " + e.getMessage());
        }
    }

    @Transactional
    public Persona crearPersonaRetorno(String nombre, String apellido, String telefono, String correoElectronico) throws Exception {
        validar(nombre, apellido, telefono, correoElectronico);
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setTelefono(telefono);
        persona.setCorreoElectronico(correoElectronico);
        persona.setEliminado(false);
        personaRepository.save(persona);
        return persona;
    }

    public void validar(String nombre, String apellido, String telefono, String correoElectronico) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (nombre.length() > 50) {
            throw new Exception("El nombre no puede superar los 50 caracteres");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("El apellido no puede estar vacío");
        }
        if (apellido.length() > 50) {
            throw new Exception("El apellido no puede superar los 50 caracteres");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new Exception("El teléfono no puede estar vacío");
        }
        if (!telefono.matches("\\+?[0-9]{7,15}")) {
            throw new Exception("El teléfono debe contener solo números y puede incluir el prefijo internacional");
        }
        if (correoElectronico == null || correoElectronico.trim().isEmpty()) {
            throw new Exception("El correo electrónico no puede estar vacío");
        }
        if (!correoElectronico.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new Exception("El correo electrónico debe tener un formato válido");
        }
    }

    @Transactional
    public void modificarPersona(String idPersona, String nombre, String apellido, String telefono, String correoElectronico) throws Exception {
        try {
            validar(nombre, apellido, telefono, correoElectronico);
            Optional<Persona> persona = personaRepository.findById(idPersona);
            if (persona.isPresent()) {
                Persona personaActual = persona.get();
                personaActual.setNombre(nombre);
                personaActual.setApellido(apellido);
                personaActual.setTelefono(telefono);
                personaActual.setCorreoElectronico(correoElectronico);
                personaRepository.save(personaActual);
            } else {
                throw new Exception("Persona no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar persona: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarPersona(String idPersona) throws Exception {
        try {
            Persona persona = buscarPersona(idPersona);
            persona.setEliminado(true);
            personaRepository.save(persona);
        } catch (Exception e) {
            throw new Exception("Error al eliminar persona: " + e.getMessage());
        }
    }

    public Persona buscarPersona(String idPersona) throws Exception {
        try {
            Optional<Persona> persona = personaRepository.findById(idPersona);
            if (persona.isPresent()) {
                return persona.get();
            } else {
                throw new Exception("Persona no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar persona: " + e.getMessage());
        }
    }

    @Transactional
    public List<Persona> listarPersonas() throws Exception {
        try {
            return personaRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Persona> listarPersonasActivas() throws Exception {
        try {
            return personaRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}