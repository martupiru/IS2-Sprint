package sprint.tinder.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sprint.tinder.services.NotificacionServicio;

@RestController
@RequestMapping("/email")
public class NotificacionControlador {
    private final NotificacionServicio notificacionServicio;
    public NotificacionControlador(final NotificacionServicio notificacionServicio) {
        this.notificacionServicio = notificacionServicio;
    }
    @PostMapping("/send")
    public String enviarMail(
            @RequestParam String mail,
            @RequestParam String titulo,
            @RequestParam String cuerpo
    ){
        this.notificacionServicio.enviar(mail, titulo, cuerpo);
        return "Mail enviado a " + mail;
    }

}
