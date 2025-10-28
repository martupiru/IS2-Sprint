package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Pais;
import com.gimnasio.gimnasio.repositories.PaisRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;

    @Transactional
    public void crearPais(String nombre) throws Exception {
        try {
            validar(nombre);
            Pais pais = new Pais();
            pais.setNombre(nombre);
            pais.setEliminado(false);
            paisRepository.save(pais);
        } catch (Exception e) {
            throw new Exception("Error al crear país: " + e.getMessage());
        }
    }


    public void validar(String nombre) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (nombre.length() > 50) {
            throw new Exception("El nombre no puede superar los 50 caracteres");
        }
    }

    @Transactional
    public void modificarPais(String idPais, String nombre) throws Exception {
        try {
            validar(nombre);
            Optional<Pais> pais = paisRepository.findById(idPais);
            if (pais.isPresent()) {
                Pais paisActual = pais.get();
                paisActual.setNombre(nombre);
                paisRepository.save(paisActual);
            } else {
                throw new Exception("País no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar país: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarPais(String idPais) throws Exception {
        try {
            Pais pais = buscarPais(idPais);
            pais.setEliminado(true);
            paisRepository.save(pais);
        } catch (Exception e) {
            throw new Exception("Error al eliminar país: " + e.getMessage());
        }
    }


    public Pais buscarPais(String idPais) throws Exception {
        try {
            Optional<Pais> pais = paisRepository.findById(idPais);
            if (pais.isPresent()) {
                return pais.get();
            } else {
                throw new Exception("País no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar país: " + e.getMessage());
        }
    }

    @Transactional
    public List<Pais> listarPaises() throws Exception {
        try {
            return paisRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Pais> listarPaisesActivos() throws Exception {
        try {
            return paisRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Pais buscarPaisPorNombre(String nombre) throws Exception {
        try {
            Optional<Pais> pais = paisRepository.findByNombreAndEliminadoFalse(nombre);
            if (pais.isPresent()) {
                return pais.get();
            } else {
                throw new Exception("País no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar país: " + e.getMessage());
        }
    }
}