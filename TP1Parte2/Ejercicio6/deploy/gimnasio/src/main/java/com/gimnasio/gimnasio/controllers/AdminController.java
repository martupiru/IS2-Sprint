package com.gimnasio.gimnasio.controllers;

import com.gimnasio.gimnasio.DTO.DeudaSocioDTO;
import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.entities.Promocion;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.enumerations.TipoMensaje;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import com.gimnasio.gimnasio.services.CuotaMensualService;
import com.gimnasio.gimnasio.services.NotificacionService;
import com.gimnasio.gimnasio.services.PromocionService;
import com.gimnasio.gimnasio.services.SocioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private SocioService socioService;
    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private PromocionService promocionService;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CuotaMensualService cuotaMensualService;

    private boolean esAdmin(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.ADMINISTRATIVO;
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        return "views/admin/dashboard";
    }


    @GetMapping("/admin/deudas")
    public String gestionDeudas(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";

        List<Socio> socios = new ArrayList<>();
        try {
            socios = socioService.findAllByEliminadoFalse();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al obtener socios: " + e.getMessage());
            return "views/admin/deudas";
        }
        List<DeudaSocioDTO> sociosConDeuda = new ArrayList<>();

        for (Socio socio : socios) {
            List<CuotaMensual> cuotas = cuotaMensualService.listarCuotasAdeudadasPorSocio(socio);

            int mesesDeuda = cuotas.size();
            double totalDeuda = cuotas.stream()
                    .mapToDouble(c -> c.getValorCuota().getValorCuota())
                    .sum();

            if (mesesDeuda > 0) {
                sociosConDeuda.add(new DeudaSocioDTO(socio, mesesDeuda, totalDeuda));
            }
        }

        model.addAttribute("sociosConDeuda", sociosConDeuda);
        return "views/admin/deudas";
    }


    @GetMapping("/admin/promociones")
    public String gestionCampanias(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";
        try {
            List<Promocion> promociones = promocionService.listarMensajesActivos();
            model.addAttribute("promociones", promociones);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "views/admin/promociones";
    }

    @GetMapping("/admin/promociones/nuevo")
    public String nuevaPromocion(HttpSession session, Model model) throws Exception {
        if (!esAdmin(session)) return "redirect:/login";

        Promocion promocion = new Promocion();
        // setear automáticamente cantidad de socios activos
        long cantidadActivos = socioService.findAllByEliminadoFalse().size();
        promocion.setCantidadSociosEnviados(cantidadActivos);
        // tipo de mensaje fijo como PROMOCION
        promocion.setTipoMensaje(TipoMensaje.PROMOCION);

        model.addAttribute("promocion", promocion);
        return "views/admin/promocionesForm";
    }

    @PostMapping("/admin/promociones/nuevo")
    public String guardarPromocion(@ModelAttribute Promocion promocion, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        try {

            // BUSCAR EL USUARIO ADMIN
            Usuario admin = usuarioRepository.findFirstByRolAndEliminadoFalse(RolUsuario.ADMINISTRATIVO)
                    .orElseThrow(() -> new Exception("No se encontró usuario admin"));

            // CONTAR SOCIOS ACTIVOS
            long cantidadSocios = socioRepository.contarSociosActivos();
            String titulo = "PROMO";
            promocionService.crearPromocion(admin.getId(), titulo, promocion.getTexto(), promocion.getFechaEnvioPromocion(), cantidadSocios);
            LocalDate fechaHoy = LocalDate.now();
            if (promocion.getFechaEnvioPromocion().equals(fechaHoy)) {
                notificacionService.enviarPromocion(promocion.getTexto());
            }
            return "redirect:/admin/promociones";
        } catch (Exception e) {
            e.printStackTrace();
            return "views/admin/promocionesForm";
        }
    }

    @PostMapping("/admin/promociones/editar/{id}")
    public String editarPromocion(@PathVariable String id, @ModelAttribute Promocion promocion, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        // BUSCAR EL USUARIO ADMIN
        Usuario admin = usuarioRepository.findFirstByRolAndEliminadoFalse(RolUsuario.ADMINISTRATIVO)
                .orElseThrow(() -> new RuntimeException("No se encontró usuario admin"));

        // CONTAR SOCIOS ACTIVOS
        long cantidadSocios = socioRepository.contarSociosActivos();

        String titulo = "PROMO";
        try {
            promocionService.modificarPromocion(id, admin.getId(), titulo, promocion.getTexto(), promocion.getFechaEnvioPromocion(), cantidadSocios);
            return "redirect:/admin/promociones";
        } catch (Exception e) {
            e.printStackTrace();
            return "views/admin/promocionesForm";
        }
    }

    @GetMapping("/admin/promociones/editar/{id}")
    public String editarPromocionForm(@PathVariable String id, HttpSession session, Model model) throws Exception {
        if (!esAdmin(session)) return "redirect:/login";

        Promocion promocion = promocionService.buscarPromocion(id);
        model.addAttribute("promocion", promocion);
        return "views/admin/promocionesForm";
    }

    @GetMapping("/admin/cumpleaños")
    public String verCumpleanios(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";

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
        model.addAttribute("rol", "admin");
        return "views/cumpleaños";
    }
}
