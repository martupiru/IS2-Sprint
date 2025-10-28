package com.gimnasio.gimnasio.controllers.admin;

import com.gimnasio.gimnasio.entities.*;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.enumerations.TipoDocumento;
import com.gimnasio.gimnasio.repositories.LocalidadRepository;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import com.gimnasio.gimnasio.repositories.SucursalRepository;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import com.gimnasio.gimnasio.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminSocioController {

    @Autowired
    private SocioService socioService;
    @Autowired
    private DireccionService direccionService;
    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private SocioRepository socioRepository;
    @Autowired
    private PaisService paisService;
    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private LocalidadRepository localidadRepository;
    @Autowired
    private LocalidadService localidadService;
    @Autowired
    private DepartamentoService departamentoService;
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private boolean esAdmin(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRol() == RolUsuario.ADMINISTRATIVO;
    }

    //ABM socios
    @GetMapping("/admin/socios")
    public String gestionSocios(HttpSession session, Model model) {
        try {
            if (!esAdmin(session)) return "redirect:/login";
            model.addAttribute("socios", socioService.findAllByEliminadoFalse());
        }catch(Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "views/admin/socios";
    }

    @GetMapping("/admin/socios/crear")
    public String crearSocios(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";
        try {
            Socio socio = new Socio();
            model.addAttribute("socio", socio);
            model.addAttribute("tiposDocumentos", TipoDocumento.values());
            model.addAttribute("paises", paisService.listarPaisesActivos());
            model.addAttribute("provincias", provinciaService.listarProvinciasActivas());
            model.addAttribute("departamentos", departamentoService.listarDepartamentos());
            model.addAttribute("localidades", localidadService.listarLocalidadesActivas());
            model.addAttribute("sucursales", sucursalService.listarSucursalesActivas());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "views/admin/socios/crear";
    }

    @PostMapping("/admin/socios/guardar")
    public String guardarSocios(HttpSession session, @ModelAttribute("socio") Socio socio, @RequestParam("idSucursal") String idSucursal, @RequestParam("idLocalidad") String idLocalidad,@RequestParam("clave") String clave, RedirectAttributes redirectAttrs) {
        if (!esAdmin(session)) return "redirect:/login";
        try {
            Sucursal sucursal = sucursalRepository.findById(idSucursal)
                    .orElseThrow(() -> new Exception("Sucursal con ID " + idSucursal + " no encontrada"));
            Localidad localidad = localidadRepository.findById(idLocalidad)
                    .orElseThrow(() -> new Exception("Localidad con ID " + idLocalidad + " no encontrada"));
            Direccion direccion = socio.getDireccion();

            // Crear dirección
            Direccion direccionCreada = direccionService.crearDireccionObj(direccion.getCalle(), direccion.getNumeracion(), direccion.getBarrio(), direccion.getManzanaPiso(), direccion.getCasaDepartamento(), direccion.getReferencia(), localidad.getId());
            // Guardar socio
            Socio socioCreado = socioService.crearSocioObj(socio.getNombre(), socio.getApellido(), socio.getFechaNacimiento(), socio.getTipoDocumento(), socio.getNumeroDocumento(), socio.getTelefono(), socio.getCorreoElectronico(), direccionCreada, sucursal);
            // Crear usuario asociado al socio
            usuarioService.crearUsuario(socioCreado.getCorreoElectronico(), clave, RolUsuario.SOCIO, socioCreado);

            redirectAttrs.addFlashAttribute("success", "Socio creado con éxito");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al crear socio: " + e.getMessage());
        }
        return "redirect:/admin/socios";
    }

    // esto es para que muestre en los campos lo viejo
    @GetMapping("/admin/socios/editar/{numeroSocio}")
    public String editarSocios(@PathVariable("numeroSocio") Long numeroSocio, HttpSession session, Model model, RedirectAttributes redirectAttrs) {
        if (!esAdmin(session)) return "redirect:/login";
        try {
            Socio socio = socioService.buscarSocio(numeroSocio);
            model.addAttribute("socio", socio);
            model.addAttribute("tiposDocumentos", TipoDocumento.values());
            model.addAttribute("paises", paisService.listarPaisesActivos());
            model.addAttribute("provincias", provinciaService.listarProvinciasActivas());
            model.addAttribute("departamentos", departamentoService.listarDepartamentos());
            model.addAttribute("localidades", localidadService.listarLocalidadesActivas());
            model.addAttribute("sucursales", sucursalService.listarSucursalesActivas());
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al cargar socio: " + e.getMessage());
            return "redirect:/admin/socios";
        }

        return "views/admin/socios/editar";
    }

    @PostMapping("/admin/socios/actualizar")
    public String actualizarSocio(HttpSession session, @ModelAttribute("socio") Socio socio, @RequestParam("idSucursal") String idSucursal, @RequestParam("idLocalidad") String idLocalidad, @RequestParam(value = "clave", required = false) String clave, RedirectAttributes redirectAttrs) {
        if (!esAdmin(session)) return "redirect:/login";
        try {
            Long numeroSocio = socio.getNumeroSocio();
            Socio socioExistente = socioService.buscarSocio(numeroSocio);
            Sucursal sucursal = sucursalRepository.findById(idSucursal)
                    .orElseThrow(() -> new Exception("Sucursal con ID " + idSucursal + " no encontrada"));
            Localidad localidad = localidadRepository.findById(idLocalidad)
                    .orElseThrow(() -> new Exception("Localidad con ID " + idLocalidad + " no encontrada"));

            // Actualizar dirección
            Direccion direccionExistente  = socioExistente.getDireccion();
            direccionService.modificarDireccion(direccionExistente.getId(), socio.getDireccion().getCalle(), socio.getDireccion().getNumeracion(), socio.getDireccion().getBarrio(), socio.getDireccion().getManzanaPiso(), socio.getDireccion().getCasaDepartamento(), socio.getDireccion().getReferencia(), localidad.getId());
            // Actualizar socio
            socioService.modificarSocio(socio.getNombre(), socio.getApellido(), socio.getFechaNacimiento(), socio.getTipoDocumento(), socio.getNumeroDocumento(), socio.getTelefono(), socio.getCorreoElectronico(), socioExistente.getNumeroSocio(), direccionExistente, sucursal);
            // Actualizar usuario asociado al socio
            usuarioRepository.findByPersona(socioExistente).ifPresent(usuario -> {
                usuario.setNombreUsuario(socio.getCorreoElectronico()); //actualizar mail
                if (clave != null && !clave.isEmpty()) {
                    usuario.setClave(clave); // actualizar clave solo si se ingresó una nueva
                }
                usuarioRepository.save(usuario);
            });
            redirectAttrs.addFlashAttribute("success", "Socio actualizado con éxito");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al actualizar socio: " + e.getMessage());
        }
        return "redirect:/admin/socios";
    }

    @GetMapping("/admin/socios/eliminar/{numeroSocio}")
    public String eliminarSocio(@PathVariable Long numeroSocio, HttpSession session, RedirectAttributes redirectAttrs) {
        if (!esAdmin(session)) return "redirect:/login";
        try {
            socioService.deleteById(numeroSocio);
        } catch (Exception e) {
        redirectAttrs.addFlashAttribute("error", "Error al eliminar socio: " + e.getMessage());
        }
        return "redirect:/admin/socios";
    }


    // Socios ELiminados
    @GetMapping("/admin/socios/sociosEliminados")
    public String sociosEliminados(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/login";
        try {
            List<Socio> sociosEliminados = socioService.findAllByEliminadoTrue();
            model.addAttribute("sociosEliminados", sociosEliminados);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "views/admin/socios/sociosEliminados";
    }

    @GetMapping("/admin/socios/alta/{numeroSocio}")
    public String darDeAltaSocio(@PathVariable Long numeroSocio, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/login";
        //socio
        socioRepository.findByNumeroSocio(numeroSocio).ifPresent(socio -> {
            socio.setEliminado(false);
            socioRepository.save(socio);
            //usuario
            usuarioRepository.findByPersona(socio).ifPresent(usuario -> {usuario.setEliminado(false);usuarioRepository.save(usuario);});
        });
        return "redirect:/admin/socios/sociosEliminados";
    }

}
