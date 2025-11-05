package com.sprint.carrito.controllers;

import com.sprint.carrito.entities.Articulo;
import com.sprint.carrito.entities.Imagen;
import com.sprint.carrito.entities.Proveedor;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.services.ArticuloService;
import com.sprint.carrito.services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articulos")
public class ArticuloController extends BaseController<Articulo, String> {


    private final ArticuloService articuloService;
    private final ProveedorService proveedorService;

    @Autowired
    public ArticuloController(ArticuloService articuloService, ProveedorService proveedorService) {
        super(articuloService);

        this.articuloService = articuloService;
        this.proveedorService = proveedorService;

        initController(new Articulo(), "Gestión de Artículos", "Editar Artículo");
    }

    @Override
    protected void preAlta() throws ErrorServiceException {
        try {
            List<Proveedor> proveedores = proveedorService.listarActivos();
            this.model.addAttribute("proveedores", proveedores);
        } catch (Exception e) {
            throw new ErrorServiceException("No se pudieron cargar los proveedores.");
        }
    }

    @Override
    protected void preModificacion() throws ErrorServiceException {
        try {
            List<Proveedor> proveedores = proveedorService.listarActivos();
            this.model.addAttribute("proveedores", proveedores);
        } catch (Exception e) {
            throw new ErrorServiceException("No se pudieron cargar los proveedores.");
        }
    }

    @GetMapping
    public String listarArticulos(Model model, HttpSession session) throws ErrorServiceException {
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login?error=Debe iniciar sesión para ver los artículos";
        }
        model.addAttribute("entidades", articuloService.listarActivos());
        return "gestion-articulos";
    }

    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model, HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login?error=Debe iniciar sesión para ver los artículos";
        }

        model.addAttribute("entidad", new Articulo());
        model.addAttribute("tituloForm", "Nuevo Artículo");
        model.addAttribute("accionForm", "/articulos/guardar");

        try {
            // Cargar proveedores activos
            List<Proveedor> proveedores = proveedorService.listarActivos();
            model.addAttribute("proveedores", proveedores);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudieron cargar los proveedores.");
        }

        return "form-articulo";
    }

    @PostMapping("/guardar")
    public String guardarArticulo(@ModelAttribute("entidad") Articulo articulo,
                                  @RequestParam("archivoImagen") MultipartFile archivoImagen,
                                  HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login?error=Debe iniciar sesión para ver los artículos";
        }

        try {
            // Manejo de la imagen
            if (!archivoImagen.isEmpty()) {
                Imagen imagen = new Imagen();
                imagen.setNombre(archivoImagen.getOriginalFilename());
                imagen.setMime(archivoImagen.getContentType());
                imagen.setContenido(archivoImagen.getBytes());
                articulo.setImagen(imagen);
            }

            if (articulo.getProveedor() != null && articulo.getProveedor().getId() != null) {
                Proveedor proveedorReal = proveedorService.obtener(articulo.getProveedor().getId())
                        .orElseThrow(() -> new Exception("Proveedor no encontrado"));
                articulo.setProveedor(proveedorReal);
            } else {
                throw new Exception("Debe seleccionar un proveedor");
            }

            articuloService.alta(articulo);
            return "redirect:/articulos?success=Artículo agregado con éxito";

        } catch (Exception e) {
            return "redirect:/articulos/alta?error=" + e.getMessage();
        }
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

    @PostMapping("/agregar-carrito/{id}")
    public String agregarAlCarrito(@PathVariable String id, HttpSession session) {
        try {
            Optional<Articulo> articuloOpt = articuloService.obtener(id);
            if (articuloOpt.isEmpty()) {
                return "redirect:/articulos/buy?error=Articulo no encontrado";
            }
            Articulo articulo = articuloOpt.get();
            List<Articulo> carrito = (List<Articulo>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }

            carrito.add(articulo);
            session.setAttribute("carrito", carrito);
        } catch (Exception e) {
            return "redirect:/articulos/buy?error=No se pudo agregar al carrito";
        }
        return "redirect:/articulos/buy?success=Articulo agregado al carrito";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model, HttpSession session) {
        List<Articulo> carrito = (List<Articulo>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        double total = carrito.stream()
                .mapToDouble(Articulo::getPrecio)
                .sum();

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "carrito";
    }

    @PostMapping("/eliminar-carrito/{id}")
    public String eliminarDelCarrito(@PathVariable String id, HttpSession session) {
        List<Articulo> carrito = (List<Articulo>) session.getAttribute("carrito");
        if (carrito != null) {
            carrito.removeIf(a -> a.getId().equals(id));
            session.setAttribute("carrito", carrito);
        }
        return "redirect:/articulos/carrito";
    }
}