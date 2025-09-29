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
import sprint.tinder.entities.Usuario;
import sprint.tinder.entities.Zona;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.services.UsuarioServicio;
import sprint.tinder.services.ZonaServicio;

import java.util.List;

@Controller
@RequestMapping("/usuario") //Indica cual es la url que va a ejecutar este controlador
public class UsuarioControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ZonaServicio zonaServicio;

    @PostMapping("/loginUsuario")
    public String loginUsuario(@RequestParam String email, @RequestParam String clave, ModelMap modelo,
                               HttpSession session) {
        try {
            Usuario usuario = usuarioServicio.login(email, clave);
            session.setAttribute("usuariosession", usuario);
            return "redirect:/mascota/mis-mascotas";

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("email", email);
            modelo.put("clave", clave);
            return "login.html";

        } catch (Exception e) {
            e.printStackTrace();
            modelo.put("error", e.getMessage());
            return "login.html";
        }

    }
    @PostMapping("/registrar")
    public String crearUsuario(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona) {

        try {
            usuarioServicio.registrar(nombre, apellido, mail, clave1, clave2, archivo, idZona);
            modelo.put("titulo", "Bienvenido al Tinder de Mascotas. ");
            modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria. ");
            return "exito.html";

        } catch (ErrorServicio ex) {
            try {
                List<Zona> zonas = zonaServicio.listarZona();
                modelo.put("zonas", zonas);
            } catch (ErrorServicio e) {}
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);
            return "registro.html";
        }catch(Exception e) {
            e.printStackTrace();
            modelo.put("error", e.getMessage());
            return "registro.html";
        }

    }
    @GetMapping("/editar-perfil")
    public String editarPerfil(HttpSession session, ModelMap model) {
        try {
            List<Zona> zonas = zonaServicio.listarZona();
            model.put("zonas", zonas);
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            model.addAttribute("perfil", usuario);
        } catch (ErrorServicio e) {
            model.addAttribute("error", e.getMessage());
        }
        return "perfil.html";
    }

    @PostMapping("/actualizar-perfil")
    public String modificarUsuario(ModelMap modelo, HttpSession session, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona) {
        Usuario usuario = null;
        try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/inicio";
            }
            usuario = usuarioServicio.buscarUsuario(id);
            usuarioServicio.modificar(id, nombre, apellido, mail, clave2, clave2, archivo, idZona);
            session.setAttribute("usuariosession", usuario);
            return "exito.html";

        } catch (ErrorServicio ex) {
            try {
                List<Zona> zonas = zonaServicio.listarZona();
                modelo.put("zonas", zonas);
            } catch (ErrorServicio e) {}

            modelo.put("error", ex.getMessage());
            modelo.put("perfil", usuario);
            return "perfil.html";

        }catch(Exception e) {
            e.printStackTrace();
            modelo.put("error", e.getMessage());
            return "perfil.html";
        }
    }

}
