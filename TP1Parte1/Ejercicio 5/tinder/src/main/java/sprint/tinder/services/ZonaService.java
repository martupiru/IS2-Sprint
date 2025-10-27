package sprint.tinder.services;

import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sprint.tinder.entities.Zona;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.repositories.ZonaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ZonaService {

    @Autowired
    private ZonaRepository zonaRepository;

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
                Zona zonaAux = zonaRepository.buscarZonaPorNombre(nombre);
                if (zonaAux != null && !zonaAux.isEliminado()) {
                    throw new ErrorServicio("Existe una zona con el nombre indicado");
                }
            } catch (NoResultException ex) {}

            Zona zona = new Zona();
            zona.setId(UUID.randomUUID().toString());
            zona.setNombre(nombre);
            zona.setDescripcion(descripcion);
            zona.setEliminado(false);

            zonaRepository.save(zona);

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
                Zona zonaAux = zonaRepository.buscarZonaPorNombre(nombre);
                if (zonaAux != null && !zonaAux.isEliminado() && !zonaAux.getId().equals(idZona)) {
                    throw new ErrorServicio("Existe una zona con el nombre indicado");
                }
            } catch (NoResultException ex) {}

            Zona zona = buscarZona(idZona);
            zona.setNombre(nombre);
            zona.setDescripcion(descripcion);

            zonaRepository.save(zona);

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

            zonaRepository.save(zona);

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

            Optional<Zona> optional = zonaRepository.findById(idZona);
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

            return zonaRepository.findAll();

        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error de Sistemas");
        }

    }

    public Collection<Zona> listarZonaActiva()throws ErrorServicio {

        try {

            return zonaRepository.listarZonaActiva();

        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error de Sistemas");
        }

    }

}
