package com.sprint.videojuegos.controllers;

import com.sprint.videojuegos.entities.Estudio;
import com.sprint.videojuegos.error.ErrorServiceException;
import com.sprint.videojuegos.services.EstudioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/estudio")
public class EstudioWebController extends BaseController<Estudio, Long> {

    private final EstudioService estudioService;

    public EstudioWebController(EstudioService service) {
        super(service);
        this.estudioService = service;
        initController(new Estudio(), "Gestión de Estudios", "Formulario de Estudio");
        this.viewList = "views/estudios/crud";
        this.viewEdit = "views/formulario/estudio";
        this.redirectList = "redirect:/estudio/list";
    }

    @GetMapping("/list")
    public String listarEstudios(Model model) {
        try {
            List<Estudio> items = estudioService.listarActivos();
            model.addAttribute("items", items);
            model.addAttribute("titleList", this.titleList);
            model.addAttribute("nameEntityLower", this.nameEntityLower);
            return viewList;
        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
            return viewList;
        }
    }

    @GetMapping("/alta")
    public String altaEstudio(Model model) {
        model.addAttribute("item", new Estudio());
        model.addAttribute("isDisabled", false);
        model.addAttribute("titleEdit", this.titleEdit);
        model.addAttribute("nameEntityLower", this.nameEntityLower);
        return viewEdit;
    }

    @GetMapping("/consultar/{id}")
    public String consultarEstudio(@PathVariable Long id, Model model) {
        try {
            Estudio e = estudioService.getOne(id);
            model.addAttribute("item", e);
            model.addAttribute("isDisabled", true);
            model.addAttribute("titleEdit", this.titleEdit);
            model.addAttribute("nameEntityLower", this.nameEntityLower);
            return viewEdit;
        } catch (Exception ex) {
            model.addAttribute("msgError", ex.getMessage());
            return viewEdit;
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarEstudio(@PathVariable Long id, Model model) {
        try {
            Estudio e = estudioService.getOne(id);
            model.addAttribute("item", e);
            model.addAttribute("isDisabled", false);
            model.addAttribute("titleEdit", this.titleEdit);
            model.addAttribute("nameEntityLower", this.nameEntityLower);
            return viewEdit;
        } catch (Exception ex) {
            model.addAttribute("msgError", ex.getMessage());
            return viewEdit;
        }
    }

    @GetMapping("/baja/{id}")
    public String bajaEstudio(@PathVariable Long id, RedirectAttributes attributes, Model model) {
        try {
            estudioService.bajaLogica(id);
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;
        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
            return viewList;
        }
    }

    @PostMapping("/actualizar")
    public String actualizarEstudio(@ModelAttribute("item") Estudio entidad, RedirectAttributes attributes, Model model) {
        try {
            if (entidad.getId() == null) {
                estudioService.alta(entidad);
            } else {
                estudioService.modificar(entidad.getId(), entidad);
            }
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;
        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        }
    }

    @Override
    protected void preAlta() throws ErrorServiceException {
        if (this.model != null) this.model.addAttribute("accion", "alta");
    }

    @Override
    protected void preModificacion() throws ErrorServiceException {
        if (this.model != null) this.model.addAttribute("accion", "modificar");
    }

}