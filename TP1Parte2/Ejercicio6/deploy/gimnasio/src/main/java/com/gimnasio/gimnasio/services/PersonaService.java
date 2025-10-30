package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Direccion;
import com.gimnasio.gimnasio.entities.Persona;
import com.gimnasio.gimnasio.entities.Sucursal;
import com.gimnasio.gimnasio.enumerations.TipoDocumento;
import com.gimnasio.gimnasio.repositories.DireccionRepository;
import com.gimnasio.gimnasio.repositories.PersonaRepository;
import com.gimnasio.gimnasio.repositories.SucursalRepository;
import com.gimnasio.gimnasio.services.ServicioBase;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Transactional
    public void crearPersona(String nombre, String apellido, LocalDate fechaNacimiento, TipoDocumento tipoDocumento,
                             String numeroDocumento, String telefono, String correoElectronico,
                             String idDireccion, String idSucursal) throws Exception {
        try {
            validar(nombre, apellido, fechaNacimiento, tipoDocumento, numeroDocumento, telefono, correoElectronico, idDireccion, idSucursal);
            Persona persona = new Persona();
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setFechaNacimiento(fechaNacimiento);
            persona.setTipoDocumento(tipoDocumento);
            persona.setNumeroDocumento(numeroDocumento);
            persona.setTelefono(telefono);
            persona.setCorreoElectronico(correoElectronico);
            persona.setEliminado(false);
            Direccion direccion = buscarDireccion(idDireccion);
            persona.setDireccion(direccion);
            Sucursal sucursal = buscarSucursal(idSucursal);
            persona.setSucursal(sucursal);
            personaRepository.save(persona);
        } catch (Exception e) {
            throw new Exception("Error al crear persona: " + e.getMessage());
        }
    }

    public void validar(String nombre, String apellido, LocalDate fechaNacimiento, TipoDocumento tipoDocumento,
                        String numeroDocumento, String telefono, String correoElectronico,
                        String idDireccion, String idSucursal) throws Exception {
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
        if (fechaNacimiento == null) {
            throw new Exception("La fecha de nacimiento es obligatoria");
        }

        if (tipoDocumento == null) {
            throw new Exception("El tipo de documento es obligatorio");
        }
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new Exception("El número de documento no puede estar vacío");
        }
        if (numeroDocumento.length() > 30) {
            throw new Exception("El número de documento no puede superar los 30 caracteres");
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
        if (idDireccion == null || idDireccion.trim().isEmpty()) {
            throw new Exception("La dirección es requerida");
        }
        if (idSucursal == null || idSucursal.trim().isEmpty()) {
            throw new Exception("La sucursal es requerida");
        }
        if (direccionRepository.findById(idDireccion).isEmpty()) {
            throw new Exception("Dirección no encontrada");
        }
        if (sucursalRepository.findById(idSucursal).isEmpty()) {
            throw new Exception("Sucursal no encontrada");
        }
    }

    @Transactional
    public void modificarPersona(String idPersona, String nombre, String apellido, LocalDate fechaNacimiento, TipoDocumento tipoDocumento,
                                 String numeroDocumento, String telefono, String correoElectronico,
                                 String idDireccion, String idSucursal) throws Exception {
        try {
            validar(nombre, apellido, fechaNacimiento, tipoDocumento, numeroDocumento, telefono, correoElectronico, idDireccion, idSucursal);
            Optional<Persona> persona = personaRepository.findById(idPersona);
            if (persona.isPresent()) {
                Persona personaActual = persona.get();
                personaActual.setNombre(nombre);
                personaActual.setApellido(apellido);
                personaActual.setFechaNacimiento(fechaNacimiento);
                personaActual.setTipoDocumento(tipoDocumento);
                personaActual.setNumeroDocumento(numeroDocumento);
                personaActual.setTelefono(telefono);
                personaActual.setCorreoElectronico(correoElectronico);
                Direccion direccion = buscarDireccion(idDireccion);
                personaActual.setDireccion(direccion);
                Sucursal sucursal = buscarSucursal(idSucursal);
                personaActual.setSucursal(sucursal);
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


    private Direccion buscarDireccion(String idDireccion) throws Exception {
        Optional<Direccion> direccion = direccionRepository.findById(idDireccion);
        if (direccion.isPresent()) {
            return direccion.get();
        } else {
            throw new Exception("Dirección no encontrada");
        }
    }

    private Sucursal buscarSucursal(String idSucursal) throws Exception {
        Optional<Sucursal> sucursal = sucursalRepository.findById(idSucursal);
        if (sucursal.isPresent()) {
            return sucursal.get();
        } else {
            throw new Exception("Sucursal no encontrada");
        }
    }
}