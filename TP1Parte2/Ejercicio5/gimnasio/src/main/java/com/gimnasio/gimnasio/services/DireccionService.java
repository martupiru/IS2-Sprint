package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Direccion;
import com.gimnasio.gimnasio.entities.Localidad;
import com.gimnasio.gimnasio.repositories.DireccionRepository;
import com.gimnasio.gimnasio.repositories.LocalidadRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @Transactional
    public void crearDireccion(String calle, String numeracion, String barrio, String manzanaPiso, String casaDepartamento, String referencia, String idLocalidad) throws Exception {
        try {
            validar(calle, numeracion, barrio, manzanaPiso, casaDepartamento, referencia, idLocalidad);
            Direccion direccion = new Direccion();
            direccion.setCalle(calle);
            direccion.setNumeracion(numeracion);
            direccion.setBarrio(barrio);
            direccion.setManzanaPiso(manzanaPiso);
            direccion.setCasaDepartamento(casaDepartamento);
            direccion.setReferencia(referencia);
            direccion.setEliminado(false);
            Localidad localidad = buscarLocalidad(idLocalidad);
            direccion.setLocalidad(localidad);
            direccionRepository.save(direccion);
        } catch (Exception e) {
            throw new Exception("Error al crear dirección: " + e.getMessage());
        }
    }
    @Transactional
    public Direccion crearDireccionObj(String calle, String numeracion, String barrio, String manzanaPiso, String casaDepartamento, String referencia, String idLocalidad) throws Exception {
        try {
            validar(calle, numeracion, barrio, manzanaPiso, casaDepartamento, referencia, idLocalidad);
            Direccion direccion = new Direccion();
            direccion.setCalle(calle);
            direccion.setNumeracion(numeracion);
            direccion.setBarrio(barrio);
            direccion.setManzanaPiso(manzanaPiso);
            direccion.setCasaDepartamento(casaDepartamento);
            direccion.setReferencia(referencia);
            direccion.setEliminado(false);
            Localidad localidad = buscarLocalidad(idLocalidad);
            direccion.setLocalidad(localidad);
            direccionRepository.save(direccion);
            return direccion;
        } catch (Exception e) {
            throw new Exception("Error al crear dirección: " + e.getMessage());
        }
    }

    public void validar(String calle, String numeracion, String barrio, String manzanaPiso, String casaDepartamento, String referencia, String idLocalidad) throws Exception {
        if (calle == null || calle.trim().isEmpty()) {
            throw new Exception("La calle no puede estar vacía");
        }
        if (calle.length() > 100) {
            throw new Exception("La calle no puede superar los 100 caracteres");
        }
        if (numeracion == null || numeracion.trim().isEmpty()) {
            throw new Exception("La numeración no puede estar vacía");
        }
        if (numeracion.length() > 10) {
            throw new Exception("La numeración no puede superar los 10 caracteres");
        }
        if (barrio == null || barrio.trim().isEmpty()) {
            throw new Exception("El barrio no puede estar vacío");
        }
        if (barrio.length() > 50) {
            throw new Exception("El barrio no puede superar los 50 caracteres");
        }
        if (manzanaPiso == null || manzanaPiso.trim().isEmpty()) {
            throw new Exception("La manzana/piso no puede estar vacía");
        }
        if (manzanaPiso.length() > 10) {
            throw new Exception("La manzana/piso no puede superar los 10 caracteres");
        }
        if (casaDepartamento == null || casaDepartamento.trim().isEmpty()) {
            throw new Exception("La casa/departamento no puede estar vacía");
        }
        if (casaDepartamento.length() > 10) {
            throw new Exception("La casa/departamento no puede superar los 10 caracteres");
        }
        if (referencia == null || referencia.trim().isEmpty()) {
            throw new Exception("La referencia no puede estar vacía");
        }
        if (referencia.length() > 100) {
            throw new Exception("La referencia no puede superar los 100 caracteres");
        }
        if (idLocalidad == null || idLocalidad.trim().isEmpty()) {
            throw new Exception("La localidad es requerida");
        }
        Optional<Localidad> localidad = localidadRepository.findById(idLocalidad);
        if (localidad.isEmpty()) {
            throw new Exception("Localidad no encontrada");
        }
    }

    @Transactional
    public void modificarDireccion(String idDireccion, String calle, String numeracion, String barrio, String manzanaPiso, String casaDepartamento, String referencia, String idLocalidad) throws Exception {
        try {
            validar(calle, numeracion, barrio, manzanaPiso, casaDepartamento, referencia, idLocalidad);
            Optional<Direccion> direccion = direccionRepository.findById(idDireccion);
            if (direccion.isPresent()) {
                Direccion direccionActual = direccion.get();
                direccionActual.setCalle(calle);
                direccionActual.setNumeracion(numeracion);
                direccionActual.setBarrio(barrio);
                direccionActual.setManzanaPiso(manzanaPiso);
                direccionActual.setCasaDepartamento(casaDepartamento);
                direccionActual.setReferencia(referencia);
                Localidad localidad = buscarLocalidad(idLocalidad);
                direccionActual.setLocalidad(localidad);
                direccionRepository.save(direccionActual);
            } else {
                throw new Exception("Dirección no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar dirección: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarDireccion(String idDireccion) throws Exception {
        try {
            Direccion direccion = buscarDireccion(idDireccion);
            direccion.setEliminado(true);
            direccionRepository.save(direccion);
        } catch (Exception e) {
            throw new Exception("Error al eliminar dirección: " + e.getMessage());
        }
    }

    public Direccion buscarDireccion(String idDireccion) throws Exception {
        try {
            Optional<Direccion> direccion = direccionRepository.findById(idDireccion);
            if (direccion.isPresent()) {
                return direccion.get();
            } else {
                throw new Exception("Dirección no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar dirección: " + e.getMessage());
        }
    }

    @Transactional
    public List<Direccion> listarDirecciones() throws Exception {
        try {
            return direccionRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Direccion> listarDireccionesActivas() throws Exception {
        try {
            return direccionRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Direccion buscarDireccionPorCalleYNumeracion(String calle, String numeracion) throws Exception {
        try {
            Optional<Direccion> direccion = direccionRepository.findByCalleAndNumeracionAndEliminadoFalse(calle, numeracion);
            if (direccion.isPresent()) {
                return direccion.get();
            } else {
                throw new Exception("Dirección no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar dirección: " + e.getMessage());
        }
    }

    private Localidad buscarLocalidad(String idLocalidad) throws Exception {
        Optional<Localidad> localidad = localidadRepository.findById(idLocalidad);
        if (localidad.isPresent()) {
            return localidad.get();
        } else {
            throw new Exception("Localidad no encontrada");
        }
    }
}
