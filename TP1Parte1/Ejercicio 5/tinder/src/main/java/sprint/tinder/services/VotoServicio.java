package sprint.tinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sprint.tinder.entities.Mascota;
import sprint.tinder.entities.Voto;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.repositories.MascotaRepositorio;
import sprint.tinder.repositories.VotoRepositorio;

import java.util.Date;
import java.util.Optional;

@Service
public class VotoServicio {
    @Autowired
    private VotoRepositorio votoRepositorio;
    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    @Autowired
    private NotificacionServicio notificacionServicio;

    public void votar(String idUsuario, String idMascota1, String idMascota2) throws ErrorServicio {
        if(idMascota1.equals(idMascota2)){
            throw new ErrorServicio("No se vote a si mismo");
        }
        Voto voto = new Voto();
        voto.setFecha(new Date());
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota1);
        if (respuesta.isPresent()){
            Mascota mascota1 = respuesta.get();
            if (mascota1.getUsuario().getId().equals(idUsuario)){ //Chequear si es el dueño de la mascota
                voto.setMascota1(mascota1);
            } else {
                throw new ErrorServicio("Usted no es dueño de la mascota");
            }
        } else {
            throw new ErrorServicio("No se encuentra una mascota con ese identificador");
        }

        Optional<Mascota> respuesta2 = mascotaRepositorio.findById(idMascota2);
        if (respuesta2.isPresent()){
            Mascota mascota2 = respuesta2.get();
            voto.setMascota2(mascota2);
            notificacionServicio.enviar("Tu mascota ha sido votada!", "Tinder de Mascotas", mascota2.getUsuario().getMail());
            // Enviamos mail al usuario de la mascota votada
        } else {
            throw new ErrorServicio("No se encuentra una mascota con ese identificador");
        }
        votoRepositorio.save(voto); // persistimos el voto si lo demás está bien
    }

    public void responder(String idUsuario, String idVoto) throws ErrorServicio {
        Optional<Voto> respuesta = votoRepositorio.findById(idVoto);
        if (respuesta.isPresent()){
            Voto voto = respuesta.get();
            voto.setRespuesta(new Date());
            if(voto.getMascota2().getUsuario().getId().equals(idUsuario)){
                notificacionServicio.enviar("Tu voto ha sido correspondido!", "Tinder de Mascotas", voto.getMascota1().getUsuario().getMail());
                // Le avisamos al usuario de la mascota que le respondieron el voto por mail
                votoRepositorio.save(voto); // Guardamos en el repositorio
            } else {
                throw new ErrorServicio("No tiene permisos para realizar esa acción");
            }
        } else {
            throw new ErrorServicio("No existe el voto con ese identificador");
        }

    }
}
