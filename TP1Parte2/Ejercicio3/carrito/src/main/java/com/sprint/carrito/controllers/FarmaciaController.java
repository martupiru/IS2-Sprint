package com.sprint.carrito.controllers;

import com.sprint.carrito.entities.Articulo;
import com.sprint.carrito.services.ArticuloService;
import com.sprint.carrito.entities.Usuario;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class FarmaciaController {

    private final UsuarioService usuarioService;
    private final ArticuloService articuloService;

    @Autowired
    public FarmaciaController(UsuarioService usuarioService, ArticuloService articuloService) {
        this.usuarioService = usuarioService;
        this.articuloService = articuloService;
    }

    @GetMapping("/")
    public String redirectToInicio() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String inicio() {
        try {
            return "index";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        try {
            return "login";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String nombre,
                                @RequestParam String password,
                                Model model,
                                HttpSession session) {

        Optional<Usuario> usuarioOpt = usuarioService.login(nombre, password);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            session.setAttribute("usuarioLogueado", usuario);

            return "redirect:/usuario/indexLogueado";

        } else {
            model.addAttribute("error", "Email o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        // Pasamos un objeto Usuario vacío al modelo para que el formulario de Thymeleaf
        // pueda enlazar los campos a este objeto.
        model.addAttribute("usuario", new Usuario());
        return "registro"; // Devuelve el nombre del nuevo archivo HTML que crearemos
    }

    // ESTE MÉTODO RECIBE LOS DATOS DEL FORMULARIO Y CREA EL USUARIO
    @PostMapping("/registro")
    public String procesarRegistro(Usuario usuario, Model model) {
        try {
            // Llamamos al método 'alta' que ya tienes en tu UsuarioService.
            // El objeto 'usuario' ya viene poblado con los datos del formulario gracias a Spring.
            usuarioService.alta(usuario);

            // Si el registro es exitoso, redirigimos al usuario a la página de login
            // con un mensaje de éxito.
            return "redirect:/login?exito";

        } catch (ErrorServiceException e) {
            // Si ocurre un error (por ejemplo, por una validación),
            // volvemos a mostrar el formulario de registro con un mensaje de error.
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario); // Devolvemos el objeto para no borrar lo que el usuario ya escribió
            return "registro";
        }
    }

    @GetMapping("/usuario/indexLogueado")
    public String inicioLogueado(HttpSession session, Model model) {
        Object usuarioEnSesion = session.getAttribute("usuarioLogueado");

        if (usuarioEnSesion == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuarioEnSesion);

        return "usuario/indexLogueado";
    }

    @GetMapping("/buy")
    public String mostrarPaginaDeVenta(Model model) {
        try {
            List<Articulo> listaDeArticulos = articuloService.listarActivos();
            model.addAttribute("articulos", listaDeArticulos);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudieron cargar los productos.");
        }
        return "buy";
    }
}
