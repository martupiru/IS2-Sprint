package com.sprint.tinder.controllers;

import com.sprint.tinder.entities.Usuario;
import com.sprint.tinder.errors.ErrorServicio;
import com.sprint.tinder.services.VotoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VotoController {

    @Autowired
    private VotoService votoServicio;

    @PostMapping("/votar")
    public String votar(HttpSession session, @RequestParam String idMascotaPropia, @RequestParam String idMascotaVotada, RedirectAttributes redirectAttributes) {
        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null) {
                return "redirect:/login";
            }
            votoServicio.votar(login.getId(), idMascotaPropia, idMascotaVotada);
            redirectAttributes.addFlashAttribute("success", "¡Voto enviado con éxito!");

        } catch (ErrorServicio ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/mascota/explorar-mascotas?idMascotaPropia=" + idMascotaPropia;
    }
}
