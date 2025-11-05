package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Direccion;
import com.gimnasio.gimnasio.entities.Empresa;
import com.gimnasio.gimnasio.entities.Sucursal;
import com.gimnasio.gimnasio.repositories.DireccionRepository;
import com.gimnasio.gimnasio.repositories.EmpresaRepository;
import com.gimnasio.gimnasio.repositories.SucursalRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Transactional
    public void crearSucursal(String nombre, String idEmpresa, String idDireccion) throws Exception {
        try {
            validar(nombre, idEmpresa, idDireccion);
            Sucursal sucursal = new Sucursal();
            sucursal.setNombre(nombre);
            sucursal.setEliminado(false);
            Empresa empresa = buscarEmpresa(idEmpresa);
            Direccion direccion = buscarDireccion(idDireccion);
            sucursal.setEmpresa(empresa);
            sucursal.setDireccion(direccion);
            sucursalRepository.save(sucursal);
        } catch (Exception e) {
            throw new Exception("Error al crear sucursal: " + e.getMessage());
        }
    }

    public void validar(String nombre, String idEmpresa, String idDireccion) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (nombre.length() > 50) {
            throw new Exception("El nombre no puede superar los 50 caracteres");
        }
        if (idEmpresa == null || idEmpresa.trim().isEmpty()) {
            throw new Exception("La empresa es requerida");
        }
        if (idDireccion == null || idDireccion.trim().isEmpty()) {
            throw new Exception("La dirección es requerida");
        }
        if (empresaRepository.findById(idEmpresa).isEmpty()) {
            throw new Exception("Empresa no encontrada");
        }
        if (direccionRepository.findById(idDireccion).isEmpty()) {
            throw new Exception("Dirección no encontrada");
        }
    }

    @Transactional
    public void modificarSucursal(String idSucursal, String nombre, String idEmpresa, String idDireccion) throws Exception {
        try {
            validar(nombre, idEmpresa, idDireccion);
            Optional<Sucursal> sucursal = sucursalRepository.findById(idSucursal);
            if (sucursal.isPresent()) {
                Sucursal sucursalActual = sucursal.get();
                sucursalActual.setNombre(nombre);
                Empresa empresa = buscarEmpresa(idEmpresa);
                Direccion direccion = buscarDireccion(idDireccion);
                sucursalActual.setEmpresa(empresa);
                sucursalActual.setDireccion(direccion);
                sucursalRepository.save(sucursalActual);
            } else {
                throw new Exception("Sucursal no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar sucursal: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarSucursal(String idSucursal) throws Exception {
        try {
            Sucursal sucursal = buscarSucursal(idSucursal);
            sucursal.setEliminado(true);
            sucursalRepository.save(sucursal);
        } catch (Exception e) {
            throw new Exception("Error al eliminar sucursal: " + e.getMessage());
        }
    }

    public Sucursal buscarSucursal(String idSucursal) throws Exception {
        try {
            Optional<Sucursal> sucursal = sucursalRepository.findById(idSucursal);
            if (sucursal.isPresent()) {
                return sucursal.get();
            } else {
                throw new Exception("Sucursal no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar sucursal: " + e.getMessage());
        }
    }

    @Transactional
    public List<Sucursal> listarSucursales() throws Exception {
        try {
            return sucursalRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Sucursal> listarSucursalesActivas() throws Exception {
        try {
            return sucursalRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Sucursal buscarSucursalPorNombre(String nombre) throws Exception {
        try {
            Optional<Sucursal> sucursal = sucursalRepository.findByNombreAndEliminadoFalse(nombre);
            if (sucursal.isPresent()) {
                return sucursal.get();
            } else {
                throw new Exception("Sucursal no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar sucursal: " + e.getMessage());
        }
    }

    private Empresa buscarEmpresa(String idEmpresa) throws Exception {
        Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
        if (empresa.isPresent()) {
            return empresa.get();
        } else {
            throw new Exception("Empresa no encontrada");
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
}