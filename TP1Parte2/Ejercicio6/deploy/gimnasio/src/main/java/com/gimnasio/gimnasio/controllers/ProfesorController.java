package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import com.gimnasio.gimnasio.services.SocioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfesorController {

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private SocioService socioService;

    private boolean esProfesor(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.PROFESOR;
    }

    @GetMapping("/profesor/dashboard")
    public String dashboardProfesor(HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        return "views/profesor/dashboard";
    }

    @GetMapping("/profesor/seguimiento")
    public String seguimientoSocios(HttpSession session) {
        if (!esProfesor(session)) return "redirect:/login";
        return "views/profesor/seguimientoSocios";
    }

    @GetMapping("/profesor/cumpleaños")
    public String verCumpleanios(HttpSession session, Model model) {
        if (!esProfesor(session)) return "redirect:/login";

        List<Socio> cumpleanios = socioService.obtenerCumpleanosProximos30Dias();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Map<String, String>> cumpleaniosView = new ArrayList<>();

        for (Socio socio : cumpleanios) {
            Map<String, String> m = new HashMap<>();
            m.put("nombre", socio.getNombre());
            m.put("apellido", socio.getApellido());
            m.put("fechaNacimiento", socio.getFechaNacimiento().format(formatter));
            cumpleaniosView.add(m);
        }

        model.addAttribute("cumpleanios", cumpleaniosView);
        model.addAttribute("rol", "profesor");
        return "views/cumpleaños";
    }
}
