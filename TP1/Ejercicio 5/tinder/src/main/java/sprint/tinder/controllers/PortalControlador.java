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
import sprint.tinder.entities.Zona;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.repositories.ZonaRepositorio;
import sprint.tinder.services.UsuarioServicio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/") //Indica cual es la url que va a ejecutar este controlador
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,@RequestParam(required = false) String logout,ModelMap model){
        // @RequestParam  le indica que son parametros que vienen del html (solicitud http) cuando llena el usuario el formulario
        if (error!=null){
            model.put("error", "Usuario o clave incorrectos");
        }
        if (logout!=null){
            model.put("logout", "Ha salido correctamente");
        }
        return "login.html";
    }
    @GetMapping("/logout")
    public String logaout(HttpSession session) {
        session.setAttribute("usuariosession", null);
        return "redirect:/inicio";
    }
    @GetMapping("/registro")
    public String registro(ModelMap modelo){
        try{
            List<Zona> zonas = zonaRepositorio.findAll(); // Para listar todas las zonas cuando se abra el registro
            modelo.put("zonas", zonas);
            return "registro.html";
        } catch(Exception e){
            e.printStackTrace();
            modelo.put("error", e.getMessage());
            return "error.html";
        }

    }
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, MultipartFile archivo, @RequestParam String idZona) throws ErrorServicio{
        // @RequestParam  le indica que son parametros que vienen del html (solicitud http) cuando llena el usuario el formulario
        try {
            usuarioServicio.registrar(nombre, apellido, mail, clave1,clave2, archivo, idZona);
        } catch (ErrorServicio e) {
            List<Zona> zonas = zonaRepositorio.findAll(); // Para listar todas las zonas cuando se abra el registro
            modelo.put("zonas", zonas);
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, e);
            return "registro.html";
        }
        modelo.put("titulo", "Bienvenido al Tinder de Mascotas!");
        modelo.put("descripcion","Tu usuario fue registrado de manera exitosa"); //Mientras más dificil el mensaje mas profesional suena jajaj
        return "exito.html";
    }
}
