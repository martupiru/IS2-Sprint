package sprint.tinder.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServicio {

    @Autowired
    private JavaMailSender mailSender;

    @Async //El hilo de ejecucion no espera a que se termine de enviar el mail, sino que lo ejecuta en paralelo (Entonces el usuario no tiene que esperar a que se mande el mail)
    public void enviar(String cuerpo, String titulo, String mail) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(mail);
        mensaje.setFrom(mail);
        mensaje.setSubject(titulo);
        mensaje.setText(cuerpo);
        mailSender.send(mensaje);
    }
}
