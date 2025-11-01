package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.BaseEntity;
import com.sprint.contactos.services.BaseService;
import com.sprint.contactos.services.ErrorServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

        this.viewList= "view/l"+ nameClass;
        this.redirectList= "redirect:/"+nameEntityLower+"/listar";
        this.viewEdit= "view/e" + nameClass;
        this.titleList= titleList;
        this.titleEdit= titleEdit;
    }

    private String getNameEntity(T object){
        return ((((T) object).getClass()).getSimpleName());
    }

    @GetMapping("/listar")
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

    @GetMapping("/alta")
    public String crear(Model model) {
        try {
            this.model = model;
            this.model.addAttribute("item", entity);
            this.model.addAttribute("isDisabled", false);
            this.model.addAttribute("titleEdit", titleEdit);
            this.model.addAttribute("nameEntityLower", nameEntityLower);
            preAlta();
            return viewEdit;

        } catch(ErrorServiceException e) {
            this.model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        } catch(Exception e) {
            this.model.addAttribute("msgError", "Error de Sistema");
            return viewEdit;
        }
    }

    @GetMapping("/consultar/{id}")
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

    @GetMapping("/modificar/{id}")
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

    @GetMapping("/baja/{id}")
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

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute("item") T entidad, RedirectAttributes attributes, Model model) {
        try {
            this.model = model;
            preActualizacion();

            System.out.println("Entidad a guardar: " + entidad);
            System.out.println("ID es nulo? " + (entidad.getId() == null));

            if(entidad.getId() == null) {
                System.out.println("Ejecutando alta...");
                service.alta(entidad);
                System.out.println("Alta completada");
            } else {
                System.out.println("Ejecutando modificar...");
                service.modificar(entidad.getId(), entidad);
                System.out.println("Modificación completada");
            }

            postActualizacion();
            attributes.addFlashAttribute("msgExito", "La acción fue realizada correctamente.");
            return redirectList;

        } catch(ErrorServiceException e) {
            System.out.println("Error service: " + e.getMessage());
            e.printStackTrace();
            this.model.addAttribute("msgError", e.getMessage());
            return viewEdit;
        } catch(Exception e) {
            System.out.println("Error general: " + e.getMessage());
            e.printStackTrace();
            this.model.addAttribute("msgError", "Error de Sistema: " + e.getMessage());
            return viewEdit;
        }
    }

    @GetMapping("/cancelar")
    public String cancelar() {
        return redirectList;
    }

    protected void preAlta()throws ErrorServiceException {}
    protected void preModificacion()throws ErrorServiceException {}
    protected void preBaja()throws ErrorServiceException {}
    protected void postBaja()throws ErrorServiceException {}
    protected void preActualizacion()throws ErrorServiceException {}
    protected void postActualizacion()throws ErrorServiceException {}

}
