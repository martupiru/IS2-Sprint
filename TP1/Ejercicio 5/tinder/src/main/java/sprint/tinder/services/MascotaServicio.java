package sprint.tinder.services;

import jakarta.persistence.NoResultException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sprint.tinder.entities.Foto;
import sprint.tinder.entities.Mascota;
import sprint.tinder.entities.Usuario;
import sprint.tinder.services.UsuarioServicio;
import sprint.tinder.enumerations.Sexo;
import sprint.tinder.enumerations.Tipo;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.repositories.MascotaRepositorio;
import sprint.tinder.repositories.UsuarioRepositorio;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class MascotaServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private FotoServicio fotoServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Transactional //Si el metodo se ejecuta sin largar excepciones, entonces se hace un commit a la base de datos y se aplican todos los cambios. Si hay una excepcion, se hace un rollback y no se aplica nada a la BD
    public void agregarMascota(String idUsuario, String nombre, Sexo sexo, MultipartFile archivo, Tipo tipo) throws ErrorServicio {
        try {
            Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
            validar(nombre, sexo);
            try { // Verificar si ya existe (no se pueden tener 2 mascotas con el mismo nombre)
                Mascota mascotaAux = mascotaRepositorio.buscarMascota(idUsuario, nombre);
                if (mascotaAux != null && !mascotaAux.isEliminado()) {
                    throw new ErrorServicio("Ya cargó esa mascota en el sistema");
                }
            } catch (NoResultException ex) {}

            Mascota mascota = new Mascota();
            mascota.setNombre(nombre);
            mascota.setSexo(sexo);
            mascota.setAlta(new Date());
            Foto foto = fotoServicio.guardar(archivo);
            mascota.setFoto(foto);
            mascota.setTipo(tipo);
            mascota.setUsuario(usuario);

            mascotaRepositorio.save(mascota);
        } catch (ErrorServicio e) {
            throw e;
        }

    }

    public void validar(String nombre, Sexo sexo) throws ErrorServicio {
        try {
            if (nombre == null || nombre.isEmpty()){
                throw new ErrorServicio("La mascota debe tener nombre");
            }
            if (sexo == null){
                throw new ErrorServicio("Debe especificar el sexo de la mascota");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ErrorServicio("Error de Sistemas");
        }

    }
    @Transactional
    public void modificar(String idUsuario,String idMascota, String nombre, Sexo sexo, MultipartFile archivo, Tipo tipo) throws ErrorServicio {
        try {
            validar(nombre, sexo);
            Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota); // Con optional puedo ver si lo que devolvio la base de datos está presente (o sea esa mascota como respuesta a ese id)
            if (respuesta.isPresent()) {
                Mascota mascota = respuesta.get();
                if(mascota.getUsuario().getId().equals(idUsuario)) { //Verificar si el usuario es dueño de la mascota
                    mascota.setNombre(nombre);
                    mascota.setSexo(sexo);
                    String idFoto = null;
                    if(mascota.getFoto() != null){ // Verifico si ya tenía una foto
                        idFoto = mascota.getFoto().getId();
                    }
                    Foto foto = fotoServicio.actualizar(idFoto, archivo);
                    mascota.setFoto(foto);
                    mascota.setTipo(tipo);
                    Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
                    mascota.setUsuario(usuario);

                    mascotaRepositorio.save(mascota); // Guardo la mascota en el repositorio
                } else{
                    throw new ErrorServicio("Usted no es dueño de esa mascota");
                }
            } else {
                throw new ErrorServicio("No existe una mascota con ese identificador");
            }
        } catch (ErrorServicio e) {
            throw e;
        }


    }
    @Transactional
    public void eliminar(String idUsuario,String idMascota) throws ErrorServicio {
        try {
            Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota); // Con optional puedo ver si lo que devolvio la base de datos está presente (o sea esa mascota como respuesta a ese id)
            if (respuesta.isPresent()) {
                Mascota mascota = respuesta.get();
                if(mascota.getUsuario().getId().equals(idUsuario)) { //Verificar si el usuario es dueño de la mascota
                    mascota.setBaja(new Date());
                    mascotaRepositorio.save(mascota);
                } else {
                    throw new ErrorServicio("Usted no es dueño de esa mascota");
                }
            } else {
                throw new ErrorServicio("No existe una mascota con ese identificador");
            }
        } catch (ErrorServicio e) {
            throw e;
        }

    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void agregarMascota(String idUsuario, String idMascota) throws ErrorServicio {
        try {
            Usuario usuario = usuarioServicio.buscarUsuario(idUsuario);
            Mascota mascota = buscarMascota(idMascota);

            if(!mascota.getUsuario().getId().equals(idUsuario)){
                throw new ErrorServicio("Puede modificar solo mascotas de su pertenencia");
            }
            mascota.setUsuario(usuario);
            mascota.setBaja(null);
            mascotaRepositorio.save(mascota);
        }catch(ErrorServicio e) {
            throw e;
        }catch(Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error de Sistemas");
        }
    }

    @Transactional(readOnly=true)
    public Mascota buscarMascota(String idMascota) throws ErrorServicio {
        try {
            if (idMascota == null || idMascota.trim().isEmpty()) {
                throw new ErrorServicio("Debe indicar una mascota");
            }
            Optional<Mascota> optional = mascotaRepositorio.findById(idMascota);
            Mascota mascota = null;
            if (optional.isPresent()) {
                mascota= optional.get();
                if (mascota.isEliminado()){
                    throw new ErrorServicio("No se encuentra la mascota indicada");
                }
            }
            return mascota;
        }catch(ErrorServicio e) {
            throw e;
        }
    }


    public Collection<Mascota> listarMascotaPorUsuario(String idUsuario)throws ErrorServicio {
        try {
            if(idUsuario == null || idUsuario.trim().isEmpty()){
                throw new ErrorServicio("Debe indicar el usuario");
            }
            return mascotaRepositorio.listarMascotasPorUsuario(idUsuario);
        }catch(ErrorServicio e) {
            throw e;
        }
    }

    public Collection<Mascota> listarMascotaDeBaja(String idUsuario)throws ErrorServicio {
        try {
            if(idUsuario == null || idUsuario.trim().isEmpty()){
                throw new ErrorServicio("Debe indicar el usuario");
            }
            return mascotaRepositorio.listarMascotasDeBaja(idUsuario);
        }catch(ErrorServicio e) {
            throw e;
        }
    }

    public Foto obtenerFotoMascota(String id) throws Exception {
        Mascota mascota = mascotaRepositorio.findById(id)
                .orElseThrow(() -> new Exception("Mascota no encontrada"));

        if (mascota.getFoto() == null) {
            throw new Exception("La mascota no tiene foto asignada");
        }

        return mascota.getFoto();
    }



}
