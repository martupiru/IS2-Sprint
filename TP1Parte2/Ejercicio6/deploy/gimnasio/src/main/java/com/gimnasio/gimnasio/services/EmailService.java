package com.gimnasio.gimnasio.services;

import jakarta.validation.constraints.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender emailSender;

    public EmailService(final JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void enviarEmail(String hacia, String titulo, String contenido) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(hacia);
        mail.setSubject(titulo);
        mail.setText(contenido);
        mail.setFrom("emaildepruebasprint@gmail.com");
        emailSender.send(mail);
    }
}
