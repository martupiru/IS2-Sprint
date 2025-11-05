package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Pais;
import com.gimnasio.gimnasio.entities.Provincia;
import com.gimnasio.gimnasio.repositories.PaisRepository;
import com.gimnasio.gimnasio.repositories.ProvinciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Transactional
    public void crearProvincia(String nombre, String idPais) throws Exception {
        try {
            validar(nombre, idPais);
            Provincia provincia = new Provincia();
            provincia.setNombre(nombre);
            provincia.setEliminado(false);
            Pais pais = buscarPais(idPais);
            provincia.setPais(pais);
            provinciaRepository.save(provincia);
        } catch (Exception e) {
            throw new Exception("Error al crear provincia: " + e.getMessage());
        }
    }

    public void validar(String nombre, String idPais) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (nombre.length() > 50) {
            throw new Exception("El nombre no puede superar los 50 caracteres");
        }
        if (idPais == null || idPais.trim().isEmpty()) {
            throw new Exception("El país es requerido");
        }
        Optional<Pais> pais = paisRepository.findById(idPais);
        if (pais.isEmpty()) {
            throw new Exception("País no encontrado");
        }
    }

    @Transactional
    public void modificarProvincia(String idProvincia, String nombre, String idPais) throws Exception {
        try {
            validar(nombre, idPais);
            Optional<Provincia> provincia = provinciaRepository.findById(idProvincia);
            if (provincia.isPresent()) {
                Provincia provinciaActual = provincia.get();
                provinciaActual.setNombre(nombre);
                Pais pais = buscarPais(idPais);
                provinciaActual.setPais(pais);
                provinciaRepository.save(provinciaActual);
            } else {
                throw new Exception("Provincia no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar provincia: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarProvincia(String idProvincia) throws Exception {
        try {
            Provincia provincia = buscarProvincia(idProvincia);
            provincia.setEliminado(true);
            provinciaRepository.save(provincia);
        } catch (Exception e) {
            throw new Exception("Error al eliminar provincia: " + e.getMessage());
        }
    }

    public Provincia buscarProvincia(String idProvincia) throws Exception {
        try {
            Optional<Provincia> provincia = provinciaRepository.findById(idProvincia);
            if (provincia.isPresent()) {
                return provincia.get();
            } else {
                throw new Exception("Provincia no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar provincia: " + e.getMessage());
        }
    }

    @Transactional
    public List<Provincia> listarProvincias() throws Exception {
        try {
            return provinciaRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Provincia> listarProvinciasActivas() throws Exception {
        try {
            return provinciaRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public Provincia buscarProvinciaPorNombre(String nombre) throws Exception {
        try {
            Optional<Provincia> provincia = provinciaRepository.findByNombreAndEliminadoFalse(nombre);
            if (provincia.isPresent()) {
                return provincia.get();
            } else {
                throw new Exception("Provincia no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar provincia: " + e.getMessage());
        }
    }

    private Pais buscarPais(String idPais) throws Exception {
        Optional<Pais> pais = paisRepository.findById(idPais);
        if (pais.isPresent()) {
            return pais.get();
        } else {
            throw new Exception("País no encontrado");
        }
    }
}