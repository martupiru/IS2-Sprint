package com.sprint.videojuegos.controllers;

import com.sprint.videojuegos.entities.Videojuego;
import com.sprint.videojuegos.error.ErrorServiceException;
import com.sprint.videojuegos.services.CategoriaService;
import com.sprint.videojuegos.services.EstudioService;
import com.sprint.videojuegos.services.VideojuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/videojuegos")
public class VideojuegoWebController extends BaseController<Videojuego, Long> {

    private final VideojuegoService videojuegoService;
    private final CategoriaService categoriaService;
    private final EstudioService estudioService;

    public VideojuegoWebController(VideojuegoService videojuegoService, CategoriaService categoriaService, EstudioService estudioService) {
        super(videojuegoService);
        this.videojuegoService = videojuegoService;
        this.categoriaService = categoriaService;
        this.estudioService = estudioService;

        initController(new Videojuego(), "Gestión de Videojuegos", "Editar Videojuego");
    }

    @Override
    protected void preAlta() throws ErrorServiceException {
        try {
            if (this.model != null) {
                this.model.addAttribute("categorias", categoriaService.listarActivos());
                this.model.addAttribute("estudios", estudioService.listarActivos());
            }
        } catch (Exception e) {
            throw new ErrorServiceException("No se pudieron cargar categorías/estudios para alta");
        }
    }

    @Override
    protected void preModificacion() throws ErrorServiceException {
        try {
            if (this.model != null) {
                this.model.addAttribute("categorias", categoriaService.listarActivos());
                this.model.addAttribute("estudios", estudioService.listarActivos());
            }
        } catch (Exception e) {
            throw new ErrorServiceException("No se pudieron cargar categorías/estudios para modificación");
        }
    }

    @GetMapping("/crud")
    public String crud(Model model) {
        try {
            List<Videojuego> videojuegos = videojuegoService.listarActivos();
            model.addAttribute("videojuegos", videojuegos);
            return "views/crud";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/formulario/{id}")
    public String formulario(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("categorias", categoriaService.listarActivos());
            model.addAttribute("estudios", estudioService.listarActivos());
            if (id == 0) model.addAttribute("videojuego", new Videojuego());
            else model.addAttribute("videojuego", videojuegoService.getOne(id));
            return "views/formulario/videojuego";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping({"/formulario/{id}", "/formulario", "/formulario/"})
    public String guardar(
            @RequestParam("archivo") MultipartFile archivo, @Valid @ModelAttribute("videojuego") Videojuego videojuego, BindingResult result, Model model, @PathVariable(name = "id", required = false) Long id
    ) {
        try {
            if (id == null) {
                id = (videojuego.getId() != null) ? videojuego.getId() : 0L;
            }

            model.addAttribute("categorias", categoriaService.listarActivos());
            model.addAttribute("estudios", estudioService.listarActivos());

            if (result.hasErrors()) {
                return "views/formulario/videojuego";
            }

            String uploadsDir = "uploads/images/";
            Files.createDirectories(Paths.get(uploadsDir)); // crea la carpeta si hace falta
            String nombreFoto = null;
            if (!archivo.isEmpty()) {
                int index = archivo.getOriginalFilename().lastIndexOf('.');
                String extension = index >= 0 ? archivo.getOriginalFilename().substring(index) : "";
                nombreFoto = Calendar.getInstance().getTimeInMillis() + extension;
                Path rutaAbsoluta = Paths.get(uploadsDir).resolve(nombreFoto);
                Files.write(rutaAbsoluta, archivo.getBytes());
                videojuego.setImagen(nombreFoto);
            }

            Path rutaAbsoluta = id != 0 && videojuego.getImagen() != null
                    ? Paths.get(uploadsDir, videojuego.getImagen())
                    : (nombreFoto != null ? Paths.get(uploadsDir, nombreFoto) : null);

            if (id == 0) {
                if (archivo.isEmpty()) {
                    model.addAttribute("errorImagenMsg", "La imagen es requerida");
                    return "views/formulario/videojuego";
                }
                if (!validarExtension(archivo)) {
                    model.addAttribute("errorImagenMsg", "La extension no es valida");
                    return "views/formulario/videojuego";
                }
                if (archivo.getSize() >= 15_000_000) {
                    model.addAttribute("errorImagenMsg", "El peso excede 15MB");
                    return "views/formulario/videojuego";
                }
                Files.write(rutaAbsoluta, archivo.getBytes());
                videojuego.setImagen(nombreFoto);
                videojuegoService.alta(videojuego);
            } else {
                if (!archivo.isEmpty()) {
                    if (!validarExtension(archivo)) {
                        model.addAttribute("errorImagenMsg", "La extension no es valida");
                        return "views/formulario/videojuego";
                    }
                    if (archivo.getSize() >= 15_000_000) {
                        model.addAttribute("errorImagenMsg", "El peso excede 15MB");
                        return "views/formulario/videojuego";
                    }
                    Files.write(rutaAbsoluta, archivo.getBytes());
                }
                videojuegoService.modificar(id, videojuego);
            }

            return "redirect:/videojuegos/crud";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String confirmarEliminar(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("videojuego", videojuegoService.getOne(id));
            return "views/formulario/eliminar";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, Model model) {
        try {
            videojuegoService.bajaLogica(id);
            return "redirect:/videojuegos/crud";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    private boolean validarExtension(MultipartFile archivo) {
        try {
            ImageIO.read(archivo.getInputStream()).toString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

