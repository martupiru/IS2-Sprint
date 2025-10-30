package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Departamento;
import com.gimnasio.gimnasio.entities.Provincia;
import com.gimnasio.gimnasio.repositories.DepartamentoRepository;
import com.gimnasio.gimnasio.repositories.ProvinciaRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional
    public void crearDepartamento(String nombre, String idProvincia) throws Exception {
        try {
            validar(nombre, idProvincia);
            Departamento departamento = new Departamento();
            departamento.setNombre(nombre);
            departamento.setEliminado(false);
            Provincia provincia = buscarProvincia(idProvincia);
            departamento.setProvincia(provincia);
            departamentoRepository.save(departamento);
        } catch (Exception e) {
            throw new Exception("Error al crear departamento: " + e.getMessage());
        }
    }

    public void validar(String nombre, String idProvincia) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (nombre.length() > 50) {
            throw new Exception("El nombre no puede superar los 50 caracteres");
        }
        if (idProvincia == null || idProvincia.trim().isEmpty()) {
            throw new Exception("La provincia es requerida");
        }
        Optional<Provincia> provincia = provinciaRepository.findById(idProvincia);
        if (provincia.isEmpty()) {
            throw new Exception("Provincia no encontrada");
        }
    }

    @Transactional
    public void modificarDepartamento(String idDepartamento, String nombre, String idProvincia) throws Exception {
        try {
            validar(nombre, idProvincia);
            Optional<Departamento> departamento = departamentoRepository.findById(idDepartamento);
            if (departamento.isPresent()) {
                Departamento departamentoActual = departamento.get();
                departamentoActual.setNombre(nombre);
                Provincia provincia = buscarProvincia(idProvincia);
                departamentoActual.setProvincia(provincia);
                departamentoRepository.save(departamentoActual);
            } else {
                throw new Exception("Departamento no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar departamento: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarDepartamento(String idDepartamento) throws Exception {
        try {
            Departamento departamento = buscarDepartamento(idDepartamento);
            departamento.setEliminado(true);
            departamentoRepository.save(departamento);
        } catch (Exception e) {
            throw new Exception("Error al eliminar departamento: " + e.getMessage());
        }
    }

    public Departamento buscarDepartamento(String idDepartamento) throws Exception {
        try {
            Optional<Departamento> departamento = departamentoRepository.findById(idDepartamento);
            if (departamento.isPresent()) {
                return departamento.get();
            } else {
                throw new Exception("Departamento no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar departamento: " + e.getMessage());
        }
    }

    @Transactional
    public List<Departamento> listarDepartamentos() throws Exception {
        try {
            return departamentoRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Departamento> listarDepartamentosActivos() throws Exception {
        try {
            return departamentoRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Departamento buscarDepartamentoPorNombre(String nombre) throws Exception {
        try {
            Optional<Departamento> departamento = departamentoRepository.findByNombreAndEliminadoFalse(nombre);
            if (departamento.isPresent()) {
                return departamento.get();
            } else {
                throw new Exception("Departamento no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar departamento: " + e.getMessage());
        }
    }

    private Provincia buscarProvincia(String idProvincia) throws Exception {
        Optional<Provincia> provincia = provinciaRepository.findById(idProvincia);
        if (provincia.isPresent()) {
            return provincia.get();
        } else {
            throw new Exception("Provincia no encontrada");
        }
    }
}

