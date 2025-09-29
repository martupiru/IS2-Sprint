package sprint.tinder.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sprint.tinder.entities.Mascota;
import sprint.tinder.entities.Usuario;
import sprint.tinder.enumerations.Sexo;
import sprint.tinder.enumerations.Tipo;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.services.MascotaServicio;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/mascota")
public class MascotaControlador {

    @Autowired
    private MascotaServicio mascotaServicio;

    @PostMapping("/eliminar-perfil")
    public String eliminar(HttpSession session, @RequestParam String id) {

        try {

            Usuario login = (Usuario) session.getAttribute("usuariosession");
            mascotaServicio.eliminar(login.getId(), id);

        } catch (ErrorServicio ex) {
            Logger.getLogger(MascotaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/mascota/mis-mascotas";
    }

    @PostMapping("/alta-perfil")
    public String darAlta(HttpSession session, @RequestParam String id) {

        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            mascotaServicio.agregarMascota(login.getId(), id);

        } catch (ErrorServicio ex) {
            Logger.getLogger(MascotaControlador.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(MascotaControlador.class.getName()).log(Level.SEVERE, null, ex);
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
}
