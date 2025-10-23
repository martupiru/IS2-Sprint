package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.DTO.DeudaSocioDTO;
import com.gimnasio.gimnasio.entities.*;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
import com.gimnasio.gimnasio.services.CuotaMensualService;
import com.gimnasio.gimnasio.services.RutinaService;
import com.gimnasio.gimnasio.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SocioController {

    @Autowired
    private CuotaMensualService cuotaMensualService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RutinaService rutinaService;

    private boolean esSocio(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.SOCIO;
    }

    @GetMapping("/socio/dashboard")
    public String dashboardSocio(HttpSession session) {
        if (!esSocio(session)) return "redirect:/login";
        return "views/socio/dashboard";
    }

    @GetMapping("/socio/rutinas")
    public String misRutinas(HttpSession session, Model model) {
        if (!esSocio(session)) return "redirect:/login";

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        try {
            Socio socio = usuarioService.getSocio(usuario);
            List<Rutina> rutinas = rutinaService.listarRutinasPorSocio(socio.getId());
            model.addAttribute("rutinas", rutinas);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "views/socio/rutinas";
    }

    @GetMapping("/socio/detalle-rutina/{id}")
    public String detalleRutina(@PathVariable String id, HttpSession session, Model model) {
        if (!esSocio(session)) return "redirect:/login";
        try {
            Rutina rutina = rutinaService.buscarRutina(id);

            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            Socio socio = usuarioService.getSocio(usuario);
            if (!rutina.getSocio().getId().equals(socio.getId())) {
                return "redirect:/socio/rutinas?error=Acceso denegado";
            }

            model.addAttribute("rutina", rutina);
            model.addAttribute("detalles", rutina.getDetalleRutinas());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/socio/rutinas?error=Ocurrió un error";
        }
        return "views/socio/detalle-rutina";
    }

    @GetMapping("/socio/cuotas")
    public String misCuotas(HttpSession session, Model model) {
        if (!esSocio(session)) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        try {
            Socio socio = usuarioService.getSocio(usuario);
            List<CuotaMensual> cuotas = cuotaMensualService.listarCuotasPorSocio(socio.getNumeroSocio());
            model.addAttribute("cuotas", cuotas);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "views/socio/cuotas";
    }

    @GetMapping("/socio/deuda")
    public String miDeuda(HttpSession session, Model model) {
        if (!esSocio(session)) return "redirect:/login";
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        try {
            Socio socio = usuarioService.getSocio(usuario);

            // Listar solo las cuotas adeudadas
            List<CuotaMensual> cuotasAdeudadas = cuotaMensualService.listarCuotasAdeudadasPorSocio(socio);

            int mesesDeuda = cuotasAdeudadas.size();
            double totalDeuda = cuotasAdeudadas.stream()
                    .mapToDouble(c -> c.getValorCuota().getValorCuota())
                    .sum();


            DeudaSocioDTO deudaDTO = new DeudaSocioDTO(socio, mesesDeuda, totalDeuda);

            model.addAttribute("deuda", deudaDTO);
            model.addAttribute("cuotasAdeudadas", cuotasAdeudadas);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "views/socio/deuda";
    }

}
