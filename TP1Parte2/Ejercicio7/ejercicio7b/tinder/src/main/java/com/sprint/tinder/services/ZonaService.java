package com.sprint.tinder.services;

import com.sprint.tinder.entities.Zona;
import com.sprint.tinder.errors.ErrorServicio;
import com.sprint.tinder.repositories.ZonaRepository;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ZonaService {
    @Autowired
    private ZonaRepository zonaRepositorio;

    public void validar (String nombre, String descripcion) throws ErrorServicio {
        try {
            if (nombre == null || nombre.isEmpty()){
                throw new ErrorServicio("Debe indicar el nombre de la zona");
            }
            if (descripcion == null || descripcion.isEmpty()) {
                throw new ErrorServicio("Debe indicar la descripción de la zona");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new ErrorServicio("Error");
        }
    }


    @Transactional
    public void crearZona(String nombre, String descripcion) throws ErrorServicio {

        try {

            validar(nombre, descripcion);

            try {
                Zona zonaAux = zonaRepositorio.buscarZonaPorNombre(nombre);
                if (zonaAux != null && !zonaAux.isEliminado()) {
                    throw new ErrorServicio("Existe una zona con el nombre indicado");
                }
            } catch (NoResultException ex) {}

            Zona zona = new Zona();
            zona.setNombre(nombre);
            zona.setDescripcion(descripcion);
            zona.setEliminado(false);

            zonaRepositorio.save(zona);

        }catch(ErrorServicio e) {
            throw e;
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error de Sistemas");
        }
    }

    @Transactional
    public void modificarZona(String idZona, String nombre, String descripcion) throws ErrorServicio {

        try {

            validar(nombre, descripcion);

            try {
                Zona zonaAux = zonaRepositorio.buscarZonaPorNombre(nombre);
                if (zonaAux != null && !zonaAux.isEliminado() && !zonaAux.getId().equals(idZona)) {
                    throw new ErrorServicio("Existe una zona con el nombre indicado");
                }
            } catch (NoResultException ex) {}

            Zona zona = buscarZona(idZona);
            zona.setNombre(nombre);
            zona.setDescripcion(descripcion);

            zonaRepositorio.save(zona);

        }catch(ErrorServicio e) {
            throw e;
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error de Sistemas");
        }
    }

    @Transactional
    public void eliminarZona(String idZona) throws ErrorServicio {
        try {
            Zona zona = buscarZona(idZona);
            zona.setEliminado(true);

            zonaRepositorio.save(zona);
        }catch(ErrorServicio e) {
            throw e;
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error de Sistemas");
        }
    }

    public Zona buscarZona(String idZona) throws ErrorServicio{
        try {
            if (idZona == null || idZona.trim().isEmpty()) {
                throw new ErrorServicio("Debe indicar la zona");
            }
            Optional<Zona> optional = zonaRepositorio.findById(idZona);
            Zona zona = null;
            if (optional.isPresent()) {
                zona= optional.get();
                if (zona == null || zona.isEliminado()){
                    throw new ErrorServicio("No se encuentra la zona indicada");
                }
            }
            return zona;
        } catch (ErrorServicio ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServicio("Error de sistema");
        }
    }

    @Transactional(readOnly=true)
    public List<Zona> listarZona()throws ErrorServicio {
        try {
            return zonaRepositorio.findAll();
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error de Sistemas");
        }
    }

    public Collection<Zona> listarZonaActiva()throws ErrorServicio {
        try {
            return zonaRepositorio.listarZonaActiva();
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error de Sistemas");
        }

    }
}
