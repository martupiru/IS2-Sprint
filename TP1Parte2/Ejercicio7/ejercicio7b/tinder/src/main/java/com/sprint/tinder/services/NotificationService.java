package com.sprint.tinder.services;

import com.sprint.tinder.entities.Mascota;
import com.sprint.tinder.entities.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final JavaMailSender emailSender;

    public NotificationService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    // mandar mails simples
    public void enviarMail(String hacia, String titulo, String contenido) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(hacia);
        mail.setSubject(titulo);
        mail.setText(contenido);
        mail.setFrom("emaildepruebasprint@gmail.com");
        emailSender.send(mail);
    }

    //Mandar mails con un html
    private void enviarMailHTML(String hacia, String asunto, String contenidoHTML) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(hacia);
        helper.setSubject(asunto);
        helper.setText(contenidoHTML, true); // true = es HTML
        helper.setFrom("emaildepruebasprint@gmail.com");

        emailSender.send(message);
    }

    // mail cuando reciben un voto
    public void enviarVoto(Mascota mascotaVotada, Usuario duenioVotado, Mascota mascotaQueVota, Usuario duenioQueVota) {
        String asunto = "Tinder de Mascotas - ¡Nuevo voto para " + mascotaVotada.getNombre() + "! ❤️";

        String contenidoHTML = construirEmailVoto(mascotaVotada, duenioVotado, mascotaQueVota, duenioQueVota);

        try {
            enviarMailHTML(duenioVotado.getMail().trim(), asunto, contenidoHTML);
        } catch (MessagingException e) {
            System.err.println("Error al enviar email de voto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // mail cuando responden un voto
    public void enviarRespuestaVoto(Mascota mascotaOriginal, Usuario duenioOriginal, Mascota mascotaQueRespondio, Usuario duenioQueRespondio) {
        String asunto = "Tinder de Mascotas - ¡Tu voto fue correspondido! 💕";

        String contenidoHTML = construirEmailRespuesta(mascotaOriginal, duenioOriginal, mascotaQueRespondio, duenioQueRespondio);

        try {
            enviarMailHTML(duenioOriginal.getMail().trim(), asunto, contenidoHTML);
        } catch (MessagingException e) {
            System.err.println("Error al enviar email de respuesta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // HTML para voto
    private String construirEmailVoto(Mascota mascotaVotada, Usuario duenioVotado, Mascota mascotaQueVota, Usuario duenioQueVota) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                "        .container { max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 0 20px rgba(0,0,0,0.1); }" +
                "        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; }" +
                "        .header h1 { margin: 0; font-size: 28px; }" +
                "        .content { padding: 30px; }" +
                "        .greeting { font-size: 18px; color: #333; margin-bottom: 20px; }" +
                "        .mascota-card { background-color: #f8f9fa; border-radius: 8px; padding: 20px; margin: 20px 0; text-align: center; }" +
                "        .mascota-card img { max-width: 200px; height: 200px; object-fit: cover; border-radius: 50%; border: 4px solid #667eea; }" +
                "        .mascota-card h3 { color: #667eea; margin: 15px 0 5px 0; }" +
                "        .mascota-card p { color: #666; margin: 5px 0; }" +
                "        .divider { text-align: center; margin: 20px 0; font-size: 24px; }" +
                "        .contacto { background-color: #e3f2fd; border-left: 4px solid #2196f3; padding: 15px; margin: 20px 0; }" +
                "        .contacto strong { color: #1976d2; }" +
                "        .button { display: inline-block; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 15px 30px; text-decoration: none; border-radius: 5px; margin: 20px 0; font-weight: bold; }" +
                "        .footer { background-color: #f8f9fa; padding: 20px; text-align: center; color: #666; font-size: 14px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>🐾 Tinder de Mascotas</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <p class='greeting'>¡Hola, <strong>" + duenioVotado.getNombre() + "</strong>!</p>" +
                "            <p style='font-size: 16px; color: #555;'>¡Tu mascota <strong>" + mascotaVotada.getNombre() + "</strong> ha recibido un nuevo voto! ❤️</p>" +
                "            " +
                "            <div class='mascota-card'>" +
                "                <img src='cid:mascotaVotada' alt='" + mascotaVotada.getNombre() + "' onerror=\"this.src='https://via.placeholder.com/200?text=🐾'\">" +
                "                <h3>" + mascotaVotada.getNombre() + "</h3>" +
                "                <p>" + mascotaVotada.getTipo() + " • " + mascotaVotada.getSexo() + "</p>" +
                "            </div>" +
                "            " +
                "            <div class='divider'>❤️</div>" +
                "            " +
                "            <p style='text-align: center; font-size: 16px; color: #555;'>Fue votada por:</p>" +
                "            " +
                "            <div class='mascota-card'>" +
                "                <img src='cid:mascotaQueVota' alt='" + mascotaQueVota.getNombre() + "' onerror=\"this.src='https://via.placeholder.com/200?text=🐾'\">" +
                "                <h3>" + mascotaQueVota.getNombre() + "</h3>" +
                "                <p>" + mascotaQueVota.getTipo() + " • " + mascotaQueVota.getSexo() + "</p>" +
                "                <p style='color: #888;'>Dueño/a: " + duenioQueVota.getNombre() + " " + duenioQueVota.getApellido() + "</p>" +
                "            </div>" +
                "            " +
                "            <div class='contacto'>" +
                "                <strong>📧 Contacto del dueño:</strong> " + duenioQueVota.getMail() + "" +
                "            </div>" +
                "            " +
                "            <div style='text-align: center;'>" +
                "                <a href='http://localhost:9000/voto/responder?idVoto=VOTO_ID' class='button'>💕 Responder Voto</a>" +
                "            </div>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            <p>Gracias por usar Tinder de Mascotas 🐾</p>" +
                "            <p style='font-size: 12px; color: #999;'>Este es un correo automático, por favor no responder.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }

    // HTML para respuesta de voto
    private String construirEmailRespuesta(Mascota mascotaOriginal, Usuario duenioOriginal, Mascota mascotaQueRespondio, Usuario duenioQueRespondio) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                "        .container { max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 0 20px rgba(0,0,0,0.1); }" +
                "        .header { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); color: white; padding: 30px; text-align: center; }" +
                "        .header h1 { margin: 0; font-size: 28px; }" +
                "        .content { padding: 30px; }" +
                "        .greeting { font-size: 18px; color: #333; margin-bottom: 20px; }" +
                "        .success-message { background-color: #d4edda; border-left: 4px solid #28a745; padding: 20px; margin: 20px 0; border-radius: 5px; }" +
                "        .success-message h2 { color: #155724; margin: 0 0 10px 0; font-size: 22px; }" +
                "        .mascota-card { background-color: #f8f9fa; border-radius: 8px; padding: 20px; margin: 20px 0; text-align: center; }" +
                "        .mascota-card img { max-width: 200px; height: 200px; object-fit: cover; border-radius: 50%; border: 4px solid #f5576c; }" +
                "        .mascota-card h3 { color: #f5576c; margin: 15px 0 5px 0; }" +
                "        .mascota-card p { color: #666; margin: 5px 0; }" +
                "        .divider { text-align: center; margin: 20px 0; font-size: 24px; }" +
                "        .contacto { background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; margin: 20px 0; }" +
                "        .contacto strong { color: #856404; }" +
                "        .footer { background-color: #f8f9fa; padding: 20px; text-align: center; color: #666; font-size: 14px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>💕 Tinder de Mascotas</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <p class='greeting'>¡Hola, <strong>" + duenioOriginal.getNombre() + "</strong>!</p>" +
                "            " +
                "            <div class='success-message'>" +
                "                <h2>🎉 ¡Es un Match!</h2>" +
                "                <p style='color: #155724; margin: 0;'>El voto de tu mascota <strong>" + mascotaOriginal.getNombre() + "</strong> ha sido correspondido!</p>" +
                "            </div>" +
                "            " +
                "            <div class='mascota-card'>" +
                "                <img src='cid:mascotaOriginal' alt='" + mascotaOriginal.getNombre() + "' onerror=\"this.src='https://via.placeholder.com/200?text=🐾'\">" +
                "                <h3>" + mascotaOriginal.getNombre() + "</h3>" +
                "                <p>" + mascotaOriginal.getTipo() + " • " + mascotaOriginal.getSexo() + "</p>" +
                "            </div>" +
                "            " +
                "            <div class='divider'>💕</div>" +
                "            " +
                "            <p style='text-align: center; font-size: 16px; color: #555;'>Ha hecho match con:</p>" +
                "            " +
                "            <div class='mascota-card'>" +
                "                <img src='cid:mascotaQueRespondio' alt='" + mascotaQueRespondio.getNombre() + "' onerror=\"this.src='https://via.placeholder.com/200?text=🐾'\">" +
                "                <h3>" + mascotaQueRespondio.getNombre() + "</h3>" +
                "                <p>" + mascotaQueRespondio.getTipo() + " • " + mascotaQueRespondio.getSexo() + "</p>" +
                "                <p style='color: #888;'>Dueño/a: " + duenioQueRespondio.getNombre() + " " + duenioQueRespondio.getApellido() + "</p>" +
                "            </div>" +
                "            " +
                "            <div class='contacto'>" +
                "                <strong>📧 Contacto del dueño:</strong> " + duenioQueRespondio.getMail() + "" +
                "                <p style='margin: 10px 0 0 0; color: #856404;'>¡Ya pueden ponerse en contacto para organizar un encuentro entre sus mascotas!</p>" +
                "            </div>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            <p>¡Gracias por usar Tinder de Mascotas! 🐾</p>" +
                "            <p style='font-size: 12px; color: #999;'>Este es un correo automático, por favor no responder.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}
