package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Empleado;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.entities.Persona;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.enumerations.TipoEmpleado;
import com.gimnasio.gimnasio.repositories.EmpleadoRepository;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import com.gimnasio.gimnasio.enumerations.TipoDocumento;
import com.gimnasio.gimnasio.entities.Direccion;
import com.gimnasio.gimnasio.entities.Sucursal;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional
    public void crearEmpleado(String nombre, String apellido, LocalDate fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico, Direccion direccion, Sucursal sucursal, TipoEmpleado tipoEmpleado) throws Exception {
        try {
            validar(nombre, apellido, fechaNacimiento, tipoDocumento, numeroDocumento, telefono, correoElectronico, direccion, sucursal, tipoEmpleado);
            Empleado empleado = new Empleado();
            empleado.setNombre(nombre);
            empleado.setApellido(apellido);
            empleado.setFechaNacimiento(fechaNacimiento);
            empleado.setTipoDocumento(tipoDocumento);
            empleado.setNumeroDocumento(numeroDocumento);
            empleado.setTelefono(telefono);
            empleado.setCorreoElectronico(correoElectronico);
            empleado.setDireccion(direccion);
            empleado.setSucursal(sucursal);
            empleado.setTipoEmpleado(tipoEmpleado);
            empleado.setEliminado(false);
            empleadoRepository.save(empleado);
        } catch (Exception e) {
            throw new Exception("Error al crear empleado: " + e.getMessage());
        }
    }

    public void modificarEmpleado(String id, String nombre, String apellido, LocalDate fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico, Direccion direccion, Sucursal sucursal, TipoEmpleado tipoEmpleado) throws Exception {
        try {
            Optional<Empleado> optEmpleado = empleadoRepository.findById(id);
            if (optEmpleado.isPresent()) {
                Empleado empleado = optEmpleado.get();
                validar(nombre, apellido, fechaNacimiento, tipoDocumento, numeroDocumento, telefono, correoElectronico, direccion, sucursal, tipoEmpleado);
                empleado.setNombre(nombre);
                empleado.setApellido(apellido);
                empleado.setFechaNacimiento(fechaNacimiento);
                empleado.setTipoDocumento(tipoDocumento);
                empleado.setNumeroDocumento(numeroDocumento);
                empleado.setTelefono(telefono);
                empleado.setCorreoElectronico(correoElectronico);
                empleado.setDireccion(direccion);
                empleado.setSucursal(sucursal);
                empleado.setTipoEmpleado(tipoEmpleado);
                empleadoRepository.save(empleado);
            } else {
                throw new Exception("Empleado no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar empleado: " + e.getMessage());
        }
    }

    @Transactional
    public boolean deleteById(String id) throws Exception {
        try {
            Optional<Empleado> opt = empleadoRepository.findById(id);
            if (opt.isPresent()) {
                Empleado empleado = opt.get();
                empleado.setEliminado(true);
                empleadoRepository.save(empleado);
            } else {
                throw new Exception("Empleado no encontrado");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Empleado> findAll() throws Exception {
        try {
            return empleadoRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Empleado buscarEmpleado(String id) throws Exception {
        try {
            Optional<Empleado> opt = empleadoRepository.findById(id);
            if (opt.isPresent()) {
                return opt.get();
            } else {
                throw new Exception("Empleado no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar empleado: " + e.getMessage());
        }
    }

    @Transactional
    public Empleado buscarPorUsuario(Usuario usuario) throws Exception {
        if (usuario == null || usuario.getId() == null) {
            throw new Exception("Usuario no encontrado o inválido");
        }

        return empleadoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new Exception("Empleado no encontrado para el usuario logueado"));
    }


    @Transactional
    public List<Empleado> findAllByEliminadoFalse() throws Exception {
        try {
            return empleadoRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Empleado findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Empleado> opt = empleadoRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void validar(String nombre, String apellido, LocalDate fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico, Direccion direccion, Sucursal sucursal, TipoEmpleado tipoEmpleado) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("El apellido no puede estar vacío.");
        }
        if (fechaNacimiento == null) {
            throw new Exception("La fecha de nacimiento es obligatoria.");
        }
        if (tipoDocumento == null) {
            throw new Exception("El tipo de documento es obligatorio.");
        }
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new Exception("El número de documento no puede estar vacío.");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new Exception("El teléfono no puede estar vacío.");
        }
        if (correoElectronico == null || correoElectronico.trim().isEmpty()) {
            throw new Exception("El correo electrónico no puede estar vacío.");
        }
        if (!correoElectronico.contains("@")) {
            throw new Exception("El correo electrónico no tiene un formato válido.");
        }
        if (direccion == null) {
            throw new Exception("La dirección es obligatoria.");
        }
        if (sucursal == null) {
            throw new Exception("La sucursal es obligatoria.");
        }
        if (tipoEmpleado == null) {
            throw new Exception("El tipo de empleado es obligatorio.");
        }
    }

    // a rezar
}
// falta el asociar