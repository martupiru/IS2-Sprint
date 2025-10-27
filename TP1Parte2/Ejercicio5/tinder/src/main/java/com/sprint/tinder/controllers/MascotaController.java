package com.sprint.tinder.controllers;

import com.sprint.tinder.entities.Mascota;
import com.sprint.tinder.entities.Usuario;
import com.sprint.tinder.enumerations.Sexo;
import com.sprint.tinder.enumerations.Tipo;
import com.sprint.tinder.errors.ErrorServicio;
import com.sprint.tinder.services.MascotaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/mascota")
public class MascotaController {
    @Autowired
    private MascotaService mascotaServicio;

    @PostMapping("/eliminar-perfil")
    public String eliminar(HttpSession session, @RequestParam String id) {
        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            mascotaServicio.eliminar(login.getId(), id);

        } catch (ErrorServicio ex) {
            Logger.getLogger(MascotaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/mascota/mis-mascotas";
    }

    @PostMapping("/alta-perfil")
    public String darAlta(HttpSession session, @RequestParam String id) {

        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            mascotaServicio.agregarMascota(login.getId(), id);

        } catch (ErrorServicio ex) {
            Logger.getLogger(MascotaController.class.getName()).log(Level.SEVERE, null, ex);

        }
        return "redirect:/mascota/mis-mascotas";
    }

    @GetMapping("/debaja-mascotas")
    public String mascotasDeBaja(HttpSession session, ModelMap model) {

        try {

            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null) {
                return "redirect:/login";
            }

            Collection<Mascota> mascotas = mascotaServicio.listarMascotaDeBaja(login.getId());
            model.put("mascotas", mascotas);

            return "mascotasdebaja";

        }catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("/mis-mascotas")
    public String misMascotas(HttpSession session, ModelMap model) {

        try {

            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null) {
                return "redirect:/login";
            }

            Collection<Mascota> mascotas = mascotaServicio.listarMascotaPorUsuario(login.getId());
            model.put("mascotas", mascotas);

            return "mascotas";

        }catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("/editar-perfil")
    public String editarPerfil(HttpSession session, @RequestParam(required = false) String id, @RequestParam(required = false) String accion, ModelMap model) {

        if (accion == null) {
            accion = "Crear";
        }

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null) {
            return "redirect:/login";
        }
        Mascota mascota = new Mascota();
        if (id != null && !id.isEmpty()) {
            try {
                mascota = mascotaServicio.buscarMascota(id);
            } catch (ErrorServicio ex) {
                Logger.getLogger(MascotaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        model.put("perfil", mascota);
        model.put("accion", accion);
        model.put("sexos", Sexo.values());
        model.put("tipos", Tipo.values());
        return "mascota.html";
    }

    @PostMapping("/actualizar-perfil")
    public String crarMascota(ModelMap modelo, HttpSession session, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam Sexo sexo, @RequestParam Tipo tipo) {

        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null) {
            return "redirect:/inicio";
        }

        try {

            if (id == null || id.isEmpty()) {
                mascotaServicio.agregarMascota(login.getId(), nombre, sexo, archivo, tipo);
            } else {
                mascotaServicio.modificar(login.getId(), id, nombre, sexo,archivo, tipo);
            }

            return "redirect:/inicio";

        } catch (ErrorServicio ex) {

            Mascota mascota = new Mascota();
            mascota.setId(id);
            mascota.setNombre(nombre);
            mascota.setSexo(sexo);
            mascota.setTipo(tipo);

            modelo.put("accion", "Actualizar");
            modelo.put("sexos", Sexo.values());
            modelo.put("tipos", Tipo.values());
            modelo.put("error", ex.getMessage());
            modelo.put("perfil", login);

            return "mascota.html";
        }
    }

    @GetMapping("/explorar-mascotas")
    public String explorarMascotas(HttpSession session, @RequestParam(required = false) String idMascotaPropia, ModelMap model) {
        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null) {
                return "redirect:/login";
            }
            // Obtenmer mascotas
            Collection<Mascota> misMascotas = mascotaServicio.listarMascotaPorUsuario(login.getId());
            model.put("misMascotas", misMascotas);
            //Si no tiene mascotas, redirigir a crear una
            if (misMascotas == null || misMascotas.isEmpty()) {
                model.put("error", "Debes tener al menos una mascota para explorar");
                return "redirect:/mascota/editar-perfil";
            }
            if (idMascotaPropia != null && !idMascotaPropia.isEmpty()) {
                Mascota mascotaSeleccionada = mascotaServicio.buscarMascota(idMascotaPropia);
                model.put("mascotaSeleccionada", mascotaSeleccionada);
                // Filtra por el tipo de mascota que tiene el usuario seleccionada
                Tipo tipoMascota = mascotaSeleccionada.getTipo();
                Collection<Mascota> mascotas = mascotaServicio.listarMascotasPorTipo(login.getId(), tipoMascota);
                model.put("mascotas", mascotas);
            }
            return "mascotas_explorar";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/inicio";
        }
    }
}
