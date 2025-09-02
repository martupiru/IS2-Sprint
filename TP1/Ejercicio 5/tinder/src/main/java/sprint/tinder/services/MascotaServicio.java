package sprint.tinder.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sprint.tinder.entities.Foto;
import sprint.tinder.entities.Mascota;
import sprint.tinder.entities.Usuario;
import sprint.tinder.enumerations.Sexo;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.repositories.MascotaRepositorio;
import sprint.tinder.repositories.UsuarioRepositorio;

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

    @Transactional //Si el metodo se ejecuta sin largar excepciones, entonces se hace un commit a la base de datos y se aplican todos los cambios. Si hay una excepcion, se hace un rollback y no se aplica nada a la BD
    public void agregarMascota(String idUsuario, String nombre, Sexo sexo, MultipartFile archivo) throws ErrorServicio {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        validar(nombre, sexo);
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setSexo(sexo);
        mascota.setAlta(new Date());
        Foto foto = fotoServicio.guardar(archivo);
        mascota.setFoto(foto);

        mascotaRepositorio.save(mascota);
    }
    public void validar(String nombre, Sexo sexo) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("La mascota debe tener nombre");
        }
        if (sexo == null){
            throw new ErrorServicio("Debe especificar el sexo de la mascota");
        }
    }
    @Transactional
    public void modificar(String idUsuario,String idMascota, String nombre, Sexo sexo, MultipartFile archivo) throws ErrorServicio {
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

                mascotaRepositorio.save(mascota); // Guardo la mascota en el repositorio
            } else{
                throw new ErrorServicio("Usted no es dueño de esa mascota");
            }
        } else {
            throw new ErrorServicio("No existe una mascota con ese identificador");
        }

    }
    @Transactional
    public void eliminar(String idUsuario,String idMascota) throws ErrorServicio {
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

    }

}
