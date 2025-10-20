package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Departamento;
import com.gimnasio.gimnasio.entities.Localidad;
import com.gimnasio.gimnasio.repositories.DepartamentoRepository;
import com.gimnasio.gimnasio.repositories.LocalidadRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LocalidadService {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Transactional
    public void crearLocalidad(String nombre, String codigoPostal, String idDepartamento) throws Exception {
        try {
            validar(nombre, codigoPostal, idDepartamento);
            Localidad localidad = new Localidad();
            localidad.setNombre(nombre);
            localidad.setCodigoPostal(codigoPostal);
            localidad.setEliminado(false);
            Departamento departamento = buscarDepartamento(idDepartamento);
            localidad.setDepartamento(departamento);
            localidadRepository.save(localidad);
        } catch (Exception e) {
            throw new Exception("Error al crear localidad: " + e.getMessage());
        }
    }

    public void validar(String nombre, String codigoPostal, String idDepartamento) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (nombre.length() > 50) {
            throw new Exception("El nombre no puede superar los 50 caracteres");
        }
        if (codigoPostal == null || codigoPostal.trim().isEmpty()) {
            throw new Exception("El código postal no puede estar vacío");
        }
        if (codigoPostal.length() > 10) {
            throw new Exception("El código postal no puede superar los 10 caracteres");
        }
        if (idDepartamento == null || idDepartamento.trim().isEmpty()) {
            throw new Exception("El departamento es requerido");
        }
        Optional<Departamento> departamento = departamentoRepository.findById(idDepartamento);
        if (departamento.isEmpty()) {
            throw new Exception("Departamento no encontrado");
        }
    }

    @Transactional
    public void modificarLocalidad(String idLocalidad, String nombre, String codigoPostal, String idDepartamento) throws Exception {
        try {
            validar(nombre, codigoPostal, idDepartamento);
            Optional<Localidad> localidad = localidadRepository.findById(idLocalidad);
            if (localidad.isPresent()) {
                Localidad localidadActual = localidad.get();
                localidadActual.setNombre(nombre);
                localidadActual.setCodigoPostal(codigoPostal);
                Departamento departamento = buscarDepartamento(idDepartamento);
                localidadActual.setDepartamento(departamento);
                localidadRepository.save(localidadActual);
            } else {
                throw new Exception("Localidad no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar localidad: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarLocalidad(String idLocalidad) throws Exception {
        try {
            Localidad localidad = buscarLocalidad(idLocalidad);
            localidad.setEliminado(true);
            localidadRepository.save(localidad);
        } catch (Exception e) {
            throw new Exception("Error al eliminar localidad: " + e.getMessage());
        }
    }

    public Localidad buscarLocalidad(String idLocalidad) throws Exception {
        try {
            Optional<Localidad> localidad = localidadRepository.findById(idLocalidad);
            if (localidad.isPresent()) {
                return localidad.get();
            } else {
                throw new Exception("Localidad no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar localidad: " + e.getMessage());
        }
    }

    @Transactional
    public List<Localidad> listarLocalidades() throws Exception {
        try {
            return localidadRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Localidad> listarLocalidadesActivas() throws Exception {
        try {
            return localidadRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Localidad buscarLocalidadPorNombre(String nombre) throws Exception {
        try {
            Optional<Localidad> localidad = localidadRepository.findByNombreAndEliminadoFalse(nombre);
            if (localidad.isPresent()) {
                return localidad.get();
            } else {
                throw new Exception("Localidad no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar localidad: " + e.getMessage());
        }
    }

    private Localidad buscarLocalidadPorCodigoPostal(String codigoPostal) throws Exception {
        Optional<Localidad> localidad = localidadRepository.findByCodigoPostalAndEliminadoFalse(codigoPostal);
        if (localidad.isPresent()) {
            return localidad.get();
        } else {
            throw new Exception("Localidad no encontrada");
        }
    }

    private Departamento buscarDepartamento(String idDepartamento) throws Exception {
        Optional<Departamento> departamento = departamentoRepository.findById(idDepartamento);
        if (departamento.isPresent()) {
            return departamento.get();
        } else {
            throw new Exception("Departamento no encontrado");
        }
    }
}