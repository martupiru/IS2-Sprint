package com.sprint.part2ej1.services;

import com.sprint.part2ej1.entities.Empresa;
import com.sprint.part2ej1.repositories.EmpresaRepository;
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
    public void crearEmpresa(String razonSocial) throws Exception {
        try {
            validar(razonSocial);
            Empresa empresa = new Empresa();
            empresa.setRazonSocial(razonSocial);
            empresa.setEliminado(false);
            empresaRepository.save(empresa);
        } catch (Exception e) {
            throw new Exception("Error al crear empresa: " + e.getMessage());
        }
    }

    public void validar(String razonSocial) throws Exception {
        if (razonSocial == null || razonSocial.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
    }

    @Transactional
    public void modificarEmpresa(String idEmpresa, String razonSocial) throws Exception {
        try {
            validar(razonSocial);
            Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
            if (empresa.isPresent()) {
                Empresa empresaActual = empresa.get();
                empresaActual.setRazonSocial(razonSocial);
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