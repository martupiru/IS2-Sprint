package com.sprint.part2ej1.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {

    private EmailService emailService;

    public SchedulerTask(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 15 5 * *" , zone = "America/Argentina/Buenos_Aires")
    public void schedulePublicidad() throws Exception{
        try {
            String titulo = "Oferta especial";
            String cuerpo = "No te pierdas estas promos!";
            emailService.mailPublicidad(titulo, cuerpo);
        }
        catch (Exception e){
            throw e;
        }
    }

    @Scheduled(cron = "0 0 0 31 12 *", zone = "America/Argentina/Buenos_Aires") // segundo-minuto-hora-dia-mes-año
    public void scheduleSaludosAnioNuevo() throws Exception{
        try {
            emailService.mailAnioNuevo();
        }
        catch (Exception e){
            throw e;
        }
    }

}
