package com.sprint.videojuegos.controllers;

import com.sprint.videojuegos.entities.BaseEntity;
import com.sprint.videojuegos.error.ErrorServiceException;
import com.sprint.videojuegos.services.BaseService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

public abstract class BaseController<T extends BaseEntity<ID>, ID> {

    protected final BaseService<T, ID> service;
    private String nameClass="";
    protected String viewList="";
    protected String redirectList="";
    protected String viewEdit="";
    protected T entity;
    protected String nameEntityLower;
    protected Model model;
    protected String titleList;
    protected String titleEdit;


    protected BaseController(BaseService<T, ID> service) {
        this.service = service;
    }

    protected void initController(T entidad, String titleList, String titleEdit) {
        this.entity= entidad;
        this.nameClass = getNameEntity(entity);
        this.nameEntityLower = nameClass.toLowerCase();
        this.viewList= "view/l"+ nameClass +".html";
        this.redirectList= "redirect:/"+nameEntityLower+"/list";
        this.viewEdit= "view/e"+ nameClass +".html";
        this.titleList= titleList;
        this.titleEdit= titleEdit;
    }

    private String getNameEntity(T object){
        return ((((T) object).getClass()).getSimpleName());
    }

    @GetMapping("/{entidad}/list")
    public String listar(Model model) {
        try {

            this.model =model;
            List<T> activos = service.listarActivos();
            this.model.addAttribute("items", activos);
            this.model.addAttribute("titleList", titleList);
            this.model.addAttribute("nameEntityLower", nameEntityLower);

        }catch(ErrorServiceException e) {
            this.model.addAttribute("msgError", e.getMessage());
        }catch(Exception e) {
            this.model.addAttribute("msgError", "Error de Sistema");
        }

        return viewList;
    }

    @GetMapping("/{entidad}/alta")
    public String crear(T entidad, Model model) {
        try {

            this.model=model;
            this.entity = entidad;
            this.model.addAttribute("item", entidad);
            this.model.addAttribute("isDisabled", false);
            this.model.addAttribute("titleEdit", titleEdit);
            this.model.addAttribute("nameEntityLower", nameEntityLower);
            preAlta();
            return viewEdit;

        }catch(ErrorServiceException e) {
            this.model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        }catch(Exception e) {
            this.model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @GetMapping("/{entidad}/consultar/{id}")
    public String consultar(@PathVariable ID id, Model model) {
        try {

            this.model=model;
            this.entity = service.obtener(id).orElseThrow(() -> new IllegalArgumentException("No encontrado: " + id));
            this.model.addAttribute("item", entity);
            this.model.addAttribute("isDisabled", true);
            this.model.addAttribute("titleEdit", titleEdit);
            this.model.addAttribute("nameEntityLower", nameEntityLower);
            preModificacion();

            return viewEdit;

        }catch(ErrorServiceException e) {
            this.model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        }catch(Exception e) {
            this.model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @GetMapping("/{entidad}/modificar/{id}")
    public String editar(@PathVariable ID id, Model model) {
        try {

            this.model=model;
            this.entity = service.obtener(id).orElseThrow(() -> new IllegalArgumentException("No encontrado: " + id));
            this.model.addAttribute("item", entity);
            this.model.addAttribute("isDisabled", false);
            this.model.addAttribute("titleEdit", titleEdit);
            this.model.addAttribute("nameEntityLower", nameEntityLower);
            preModificacion();

            return viewEdit;

        }catch(ErrorServiceException e) {
            this.model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        }catch(Exception e) {
            this.model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @GetMapping("/{entidad}/baja/{id}")
    public String eliminar(@PathVariable ID id, RedirectAttributes attributes, Model model) {
        try {

            this.model=model;

            preBaja();
            service.bajaLogica(id);
            postBaja();

            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;

        }catch(ErrorServiceException e) {
            this.model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        }catch(Exception e) {
            this.model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @PostMapping("/{entidad}/actualizar")
    public String actualizar(@ModelAttribute("item") T entidad, RedirectAttributes attributes, Model model) {
        try {

            this.model=model;
            preActualziacion();

            if(entidad.getId() == null)
                service.alta(entidad);
            else
                service.modificar(entidad.getId(), entidad);

            postActualziacion();
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;

        }catch(ErrorServiceException e) {
            this.model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        }catch(Exception e) {
            this.model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @GetMapping("/{entidad}/cancelar")
    public String cancelar() {
        return redirectList;
    }

    protected void preAlta()throws ErrorServiceException {}
    protected void preModificacion()throws ErrorServiceException {}
    protected void preBaja()throws ErrorServiceException {}
    protected void postBaja()throws ErrorServiceException {}
    protected void preActualziacion()throws ErrorServiceException {}
    protected void postActualziacion()throws ErrorServiceException {}

}