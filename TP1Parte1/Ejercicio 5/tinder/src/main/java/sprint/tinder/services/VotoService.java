package sprint.tinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sprint.tinder.entities.Mascota;
import sprint.tinder.entities.Voto;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.repositories.MascotaRepository;
import sprint.tinder.repositories.VotoRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class VotoService {
    @Autowired
    private VotoRepository votoRepositorio;
    @Autowired
    private MascotaRepository mascotaRepositorio;
    @Autowired
    private NotificationService notificacionServicio;

    @Transactional
    public void votar(String idUsuario, String idMascota1, String idMascota2) throws ErrorServicio {
        if(idMascota1.equals(idMascota2)){
            throw new ErrorServicio("No se vote a si mismo");
        }
        Voto voto = new Voto();
        voto.setFecha(new Date());

        Mascota mascota1 = null;
        Mascota mascota2 = null;

        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota1);
        if (respuesta.isPresent()){
            mascota1 = respuesta.get();
            if (mascota1.getUsuario().getId().equals(idUsuario)){
                voto.setMascota1(mascota1);
            } else {
                throw new ErrorServicio("Usted no es dueño de la mascota");
            }
        } else {
            throw new ErrorServicio("No se encuentra una mascota con ese identificador");
        }

        Optional<Mascota> respuesta2 = mascotaRepositorio.findById(idMascota2);
        if (respuesta2.isPresent()){
            mascota2 = respuesta2.get();
            voto.setMascota2(mascota2);
            votoRepositorio.save(voto); // Guardar primero, por las dudas
            // luego mandar mail
            try {
                notificacionServicio.enviarVoto(
                        mascota2, // Mascota votada
                        mascota2.getUsuario(), // Dueño de la mascota votada
                        mascota1, // Mascota que vota
                        mascota1.getUsuario() // Dueño de la mascota que vota
                );
            } catch (Exception e) {
                System.err.println("Error al enviar notificación por email: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            throw new ErrorServicio("No se encuentra una mascota con ese identificador");
        }
    }

    @Transactional
    public void responder(String idUsuario, String idVoto) throws ErrorServicio {
        Optional<Voto> respuesta = votoRepositorio.findById(idVoto);
        if (respuesta.isPresent()){
            Voto voto = respuesta.get();
            voto.setRespuesta(new Date());
            if(voto.getMascota2().getUsuario().getId().equals(idUsuario)){
                votoRepositorio.save(voto); // Guardar primero
                // Luego mandar mail
                try {
                    notificacionServicio.enviarRespuestaVoto(
                            voto.getMascota1(), // Mascota que votó antes
                            voto.getMascota1().getUsuario(), //dueño de mascota que votó
                            voto.getMascota2(), // Mascota que respondió
                            voto.getMascota2().getUsuario() // dueño de mascota que respondió
                    );
                } catch (Exception e) {
                    System.err.println("Error al enviar notificación: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                throw new ErrorServicio("No tiene permisos para realizar esa acción");
            }
        } else {
            throw new ErrorServicio("No existe el voto con ese identificador");
        }
    }
}