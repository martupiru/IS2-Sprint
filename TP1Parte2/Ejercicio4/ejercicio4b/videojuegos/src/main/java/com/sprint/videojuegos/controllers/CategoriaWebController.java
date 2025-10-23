package com.sprint.videojuegos.controllers;

import com.sprint.videojuegos.entities.Categoria;
import com.sprint.videojuegos.error.ErrorServiceException;
import com.sprint.videojuegos.services.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/categoria")
public class CategoriaWebController extends BaseController<Categoria, Long> {

    private final CategoriaService categoriaService;

    public CategoriaWebController(CategoriaService service) {
        super(service);
        this.categoriaService = service;
        initController(new Categoria(), "Gestión de Categorías", "Formulario de Categoría");
        this.viewList = "views/categorias/crud";
        this.viewEdit = "views/formulario/categoria";
        this.redirectList = "redirect:/categoria/list";
    }

    @GetMapping("/list")
    public String listarCategorias(Model model) {
        try {
            List<Categoria> items = categoriaService.listarActivos();
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
    public String altaCategoria(Model model) {
        try {
            model.addAttribute("item", new Categoria());
            model.addAttribute("isDisabled", false);
            model.addAttribute("titleEdit", this.titleEdit);
            model.addAttribute("nameEntityLower", this.nameEntityLower);
            preAlta();
            return viewEdit;
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de sistema");
            return viewEdit;
        }
    }

    @GetMapping("/consultar/{id}")
    public String consultarCategoria(@PathVariable Long id, Model model) {
        try {
            Categoria c = categoriaService.getOne(id);
            model.addAttribute("item", c);
            model.addAttribute("isDisabled", true);
            model.addAttribute("titleEdit", this.titleEdit);
            model.addAttribute("nameEntityLower", this.nameEntityLower);
            preModificacion();
            return viewEdit;
        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarCategoria(@PathVariable Long id, Model model) {
        try {
            Categoria c = categoriaService.getOne(id);
            model.addAttribute("item", c);
            model.addAttribute("isDisabled", false);
            model.addAttribute("titleEdit", this.titleEdit);
            model.addAttribute("nameEntityLower", this.nameEntityLower);
            preModificacion();
            return viewEdit;
        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        }
    }

    @GetMapping("/baja/{id}")
    public String bajaCategoria(@PathVariable Long id, RedirectAttributes attributes, Model model) {
        try {
            categoriaService.bajaLogica(id);
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;
        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
            return viewList;
        }
    }

    @PostMapping("/actualizar")
    public String actualizarCategoria(@ModelAttribute("item") Categoria entidad, RedirectAttributes attributes, Model model) {
        try {
            if (entidad.getId() == null) {
                categoriaService.alta(entidad);
            } else {
                categoriaService.modificar(entidad.getId(), entidad);
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