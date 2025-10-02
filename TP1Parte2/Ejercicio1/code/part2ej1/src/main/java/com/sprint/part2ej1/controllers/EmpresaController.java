package com.sprint.part2ej1.controllers;

import com.sprint.part2ej1.entities.Empresa;
import com.sprint.part2ej1.entities.Usuario;
import com.sprint.part2ej1.services.EmpresaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // Listado de empresas
    @GetMapping("/detalle")
    public String mostrarABMEmpresas(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        try {
            List<Empresa> empresas = empresaService.listarEmpresasActivas();
            model.addAttribute("empresas", empresas);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "views/usuario/Menu/empresas/detalle";
    }

    // Formulario nueva empresa
    @GetMapping("/crear")
    public String mostrarFormularioCrearEmpresa(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("empresa", new Empresa());
        return "views/usuario/Menu/empresas/FormEmpresa";
    }

    @PostMapping("/crear")
    public String crearEmpresa(HttpSession session,
                               @RequestParam String razonSocial,
                               Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        try {
            empresaService.crearEmpresa(razonSocial);
            return "redirect:/usuario/empresa/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/usuario/Menu/empresas/FormEmpresa";
        }
    }

    // Editar
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarEmpresa(@PathVariable("id") String idEmpresa,
                                                 HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        try {
            Empresa empresa = empresaService.buscarEmpresa(idEmpresa);
            model.addAttribute("empresa", empresa);
            return "views/usuario/Menu/empresas/FormEmpresa";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/usuario/Menu/empresas/detalle";
        }
    }

    @PostMapping("/editar")
    public String editarEmpresa(@RequestParam String id,
                                @RequestParam String razonSocial,
                                HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        try {
            empresaService.modificarEmpresa(id, razonSocial);
            return "redirect:/usuario/empresa/detalle";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "views/usuario/Menu/empresas/FormEmpresa";
        }
    }

    // Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpresa(@PathVariable("id") String idEmpresa,
                                  HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        try {
            empresaService.eliminarEmpresa(idEmpresa);
            return "redirect:/usuario/empresa/detalle";
        } catch (Exception e) {
            return "error";
        }
    }
}
