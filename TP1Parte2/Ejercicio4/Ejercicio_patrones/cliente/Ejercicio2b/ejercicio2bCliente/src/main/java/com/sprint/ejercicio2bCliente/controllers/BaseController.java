package com.sprint.ejercicio2bCliente.controllers;


import com.sprint.ejercicio2bCliente.DTO.Identifiable;
import com.sprint.ejercicio2bCliente.services.BaseServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Controller
public abstract class BaseController<T extends Identifiable<ID>, ID> {

    protected final BaseServiceClient<T, ID> service;
    private String nameClass = "";
    protected String viewList = "";
    protected String redirectList = "";
    protected String viewEdit = "";
    protected T entity;
    protected String nameEntityLower;
    protected Model model;
    protected String titleList;
    protected String titleEdit;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected BaseController(BaseServiceClient<T, ID> service) {
        this.service = service;
    }

    /**
     * Inicializa nombres y vistas. Llamar desde el constructor del controlador concreto.
     * ejemplo: initController(new AutorDTO(), "LIST AUTOR", "EDIT AUTOR") por ejemplo;
     */
    protected void initController(T entidad, String titleList, String titleEdit) {
        this.entity = entidad;
        this.nameClass = getSimpleName(getNameEntity(entidad));
        this.nameEntityLower = nameClass.toLowerCase();
        this.viewList = "view/l" + nameClass + ".html";
        this.redirectList = "redirect:/" + nameEntityLower + "/list";
        this.viewEdit = "view/e" + nameClass + ".html";
        this.titleList = titleList;
        this.titleEdit = titleEdit;
    }

    private String getNameEntity(T object) {
        return object.getClass().getSimpleName();
    }

    @GetMapping("/list")
    public String listar(Model model) {
        try {
            this.model = model;
            List<T> activos = service.listarActivos();
            this.model.addAttribute("items", activos);
            this.model.addAttribute("titleList", titleList);
            this.model.addAttribute("nameEntityLower", nameEntityLower);
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
        }
        return viewList;
    }

    @GetMapping("/alta")
    public String crear(Model model) {
        try {
            this.model = model;
            // crear una nueva instancia vacía para el form (se pasó en initController como 'entity')
            this.model.addAttribute("item", entity);
            this.model.addAttribute("isDisabled", false);
            this.model.addAttribute("titleEdit", titleEdit);
            this.model.addAttribute("nameEntityLower", nameEntityLower);
            preAlta();
            return viewEdit;
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @GetMapping("/consultar/{id}")
    public String consultar(@PathVariable ID id, Model model) {
        try {
            this.model = model;
            T found = service.obtener(id);
            if (found == null)
                throw new IllegalArgumentException("No encontrado: " + id);

            this.model.addAttribute("item", found);
            this.model.addAttribute("isDisabled", true);
            this.model.addAttribute("titleEdit", titleEdit);
            this.model.addAttribute("nameEntityLower", nameEntityLower);
            preModificacion();
            return viewEdit;
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @GetMapping("/modificar/{id}")
    public String editar(@PathVariable ID id, Model model) {
        try {
            this.model = model;
            T found = service.obtener(id);
            if (found == null)
                throw new IllegalArgumentException("No encontrado: " + id);

            this.model.addAttribute("item", found);
            this.model.addAttribute("isDisabled", false);
            this.model.addAttribute("titleEdit", titleEdit);
            this.model.addAttribute("nameEntityLower", nameEntityLower);
            preModificacion();
            return viewEdit;
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @GetMapping("/baja/{id}")
    public String eliminar(@PathVariable ID id, RedirectAttributes attributes, Model model) {
        try {
            this.model = model;
            preBaja();
            service.bajaLogica(id);
            postBaja();
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    /**
     * El formulario debe publicar un objeto 'item' con la misma estructura del DTO (incluyendo id).
     */
    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute("item") T entidad, RedirectAttributes attributes, Model model) {
        try {
            this.model = model;
            preActualziacion();

            if (entidad.getId() == null) {
                service.alta(entidad);
            } else {
                service.modificar(entidad.getId(), entidad);
            }

            postActualziacion();
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        } catch (Exception e) {
            model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @GetMapping("/cancelar")
    public String cancelar() {
        return redirectList;
    }

    private String getSimpleName(String simple) {
        if (simple.endsWith("DTO")) {
            simple = simple.substring(0, simple.length() - 3);
        }
        return simple;
    }

    /*  para que las subclases redefinan comportamiento */
    protected void preAlta() throws ErrorServiceException {}
    protected void preModificacion() throws ErrorServiceException {}
    protected void preBaja() throws ErrorServiceException {}
    protected void postBaja() throws ErrorServiceException {}
    protected void preActualziacion() throws ErrorServiceException {}
    protected void postActualziacion() throws ErrorServiceException {}

}
