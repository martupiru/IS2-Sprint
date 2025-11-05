package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Empresa;
import com.gimnasio.gimnasio.repositories.EmpresaRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional
    public void crearEmpresa(String nombre, String telefono, String correoElectronico) throws Exception {
        try {
            validar(nombre, telefono, correoElectronico);
            Empresa empresa = new Empresa();
            empresa.setNombre(nombre);
            empresa.setTelefono(telefono);
            empresa.setCorreoElectronico(correoElectronico);
            empresa.setEliminado(false);
            empresaRepository.save(empresa);
        } catch (Exception e) {
            throw new Exception("Error al crear empresa: " + e.getMessage());
        }
    }

    public void validar(String nombre, String telefono, String correoElectronico) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (nombre.length() > 50) {
            throw new Exception("El nombre no puede superar los 50 caracteres");
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
    public void modificarEmpresa(String idEmpresa, String nombre, String telefono, String correoElectronico) throws Exception {
        try {
            validar(nombre, telefono, correoElectronico);
            Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
            if (empresa.isPresent()) {
                Empresa empresaActual = empresa.get();
                empresaActual.setNombre(nombre);
                empresaActual.setTelefono(telefono);
                empresaActual.setCorreoElectronico(correoElectronico);
                empresaRepository.save(empresaActual);
            } else {
                throw new Exception("Empresa no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar empresa: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarEmpresa(String idEmpresa) throws Exception {
        try {
            Empresa empresa = buscarEmpresa(idEmpresa);
            empresa.setEliminado(true);
            empresaRepository.save(empresa);
        } catch (Exception e) {
            throw new Exception("Error al eliminar empresa: " + e.getMessage());
        }
    }

    public Empresa buscarEmpresa(String idEmpresa) throws Exception {
        try {
            Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
            if (empresa.isPresent()) {
                return empresa.get();
            } else {
                throw new Exception("Empresa no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar empresa: " + e.getMessage());
        }
    }

    @Transactional
    public List<Empresa> listarEmpresas() throws Exception {
        try {
            return empresaRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Empresa> listarEmpresasActivas() throws Exception {
        try {
            return empresaRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Empresa buscarEmpresaPorNombre(String nombre) throws Exception {
        try {
            Optional<Empresa> empresa = empresaRepository.findByNombreAndEliminadoFalse(nombre);
            if (empresa.isPresent()) {
                return empresa.get();
            } else {
                throw new Exception("Empresa no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar empresa: " + e.getMessage());
        }
    }
}