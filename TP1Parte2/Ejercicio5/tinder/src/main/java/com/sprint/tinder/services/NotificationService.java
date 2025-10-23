package com.sprint.tinder.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final JavaMailSender emailSender;

    public NotificationService(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void enviarMail(String hacia, String titulo, String contenido) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(hacia);
        mail.setSubject(titulo);
        mail.setText(contenido);
        mail.setFrom("emaildepruebasprint@gmail.com");
        emailSender.send(mail);
    }

    /*
    public void enviarFelicitacion() {
        // Mensaje de cumpleaños a los socios que cumplan en ese mismo dia
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        int day = hoy.getDayOfMonth();
        int month = hoy.getMonthValue();

        for (Socio socio : cumpleanieros) {
            String asunto = "🎉🥳 ¡Feliz cumpleaños " + socio.getNombre() + "!🎉🥳";
            String cuerpo = "¡¡¡Hola " + socio.getNombre() + "!!!,\n\n" + "El equipo de Sport Gym te desea un muy feliz cumpleaños 🥳💯💥😁😎.\n" + "🔥🤑¡Que tengas día increíble!🤑🔥\n\n" + "Atentamente: Sport Gym.";

            //emailService.enviarEmail(socio.getCorreoElectronico(), asunto, cuerpo);
            try {
                Optional<Usuario> usuario = usuarioRepository.findByPersonaId(socio.getId());
                if (usuario.isPresent()) {
                    Usuario user = usuario.get();
                    Mensaje mensaje = mensajeService.crearMensajeObj(user.getId(), asunto, cuerpo, TipoMensaje.CUMPLEAÑOS);
                    mensajeService.enviarMensaje(mensaje.getId());
                }
            } catch (Exception e) {
                System.err.println("Error al registrar mensaje de cumpleaños: " + e.getMessage());
            }
        }
    }

     */
}
