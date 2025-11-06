package com.sprint.part2ej1.services;

import com.sprint.part2ej1.entities.Direccion;
import com.sprint.part2ej1.entities.Localidad;
import com.sprint.part2ej1.entities.Proveedor;
import com.sprint.part2ej1.entities.Usuario;
import com.sprint.part2ej1.repositories.ProveedorRepository;
import com.sprint.part2ej1.utils.HashForLogin;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {


    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private LocalidadService localidadService;

    @Transactional
    public void crearProveedor(String cuit) throws Exception{
        try {
            Proveedor user = new Proveedor();
            user.setCuit(cuit);
            proveedorRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al crear el proveedor: " + e.getMessage());
        }
    }

    @Transactional
    public void crearProveedorConPersona(String nombre, String apellido, String telefono, String correoElectronico, String cuit) throws Exception {
        try {
            // Validar campos usando el método de PersonaService
            personaService.validar(nombre, apellido, telefono, correoElectronico);

            Proveedor user = new Proveedor();
            user.setNombre(nombre);
            user.setApellido(apellido);
            user.setTelefono(telefono);
            user.setCorreoElectronico(correoElectronico);
            user.setCuit(cuit);
            user.setEliminado(false);

            proveedorRepository.save(user);

        } catch (Exception e) {
            throw new Exception("Error al crear usuario con persona: " + e.getMessage());
        }
    }

    @Transactional
    public void modificarProveedor(String idProveedor, String cuit) throws Exception{
        try {
            Optional<Proveedor> prov = proveedorRepository.findById(idProveedor);
            if (prov.isPresent()) {
                Proveedor provAct = prov.get();
                provAct.setCuit(cuit);
                proveedorRepository.save(provAct);
            } else {
                throw new Exception("Proveedor no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar proveedor: " + e.getMessage());
        }
    }
    @Transactional
    public void eliminarProveedor(String idProveedor) throws Exception{
        try {
            Proveedor prov = buscarProveedor(idProveedor);
            prov.setEliminado(true);
            proveedorRepository.save(prov);
        } catch (Exception e) {
            throw new Exception("Error al eliminar proveedor: " + e.getMessage());
        }
    }

    public Proveedor buscarProveedor(String idProveedor) throws Exception{
        try {
            Optional<Proveedor> prov = proveedorRepository.findById(idProveedor);
            if (prov.isPresent()) {
                Proveedor provActual = prov.get();
                return provActual;
            } else {
                throw new Exception("Proveedor no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar proveedor: " + e.getMessage());
        }
    }

    public Optional<Proveedor> buscarProveedorPorCuit(String cuit) {
        return proveedorRepository.findByCuit(cuit);
    }

    @Transactional
    public List<Proveedor> listarProveedoresActivos() throws Exception {
        try {
            return this.proveedorRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Proveedor guardarProveedor(Proveedor proveedor) throws Exception {
        try {
            return proveedorRepository.save(proveedor);
        } catch (Exception e) {
            throw new Exception("Error al guardar el proveedor durante la migración: " + e.getMessage());
        }
    }

    @Transactional
    public void crearProveedorConDireccion(String nombre, String apellido, String telefono,
                                           String correo, String cuit, String calle,
                                           String numeracion, String idLocalidad) throws Exception {
        try {
            // Validar que no exista el CUIT
            Optional<Proveedor> existente = proveedorRepository.findByCuit(cuit);
            if (existente.isPresent()) {
                throw new Exception("Ya existe un proveedor con el CUIT: " + cuit);
            }

            personaService.validar(nombre, apellido, telefono, correo);

            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(nombre);
            proveedor.setApellido(apellido);
            proveedor.setTelefono(telefono);
            proveedor.setCorreoElectronico(correo);
            proveedor.setCuit(cuit);
            proveedor.setEliminado(false);

            // Crear dirección
            Direccion direccion = new Direccion();
            direccion.setCalle(calle);
            direccion.setNumeracion(numeracion);
            direccion.setBarrio("");
            direccion.setManzanaPiso("");
            direccion.setCasaDepartamento("");
            direccion.setReferencia("");
            direccion.setLatitud(null);
            direccion.setLongitud(null);
            direccion.setEliminado(false);

            Localidad localidad = localidadService.buscarLocalidad(idLocalidad);
            direccion.setLocalidad(localidad);

            List<Direccion> direcciones = new ArrayList<>();
            direcciones.add(direccion);
            proveedor.setDirecciones(direcciones);

            proveedorRepository.save(proveedor);

        } catch (Exception e) {
            throw new Exception("Error al crear proveedor con dirección: " + e.getMessage());
        }
    }
}
