package com.sprint.KioscoPatrones.controllers;

import com.sprint.KioscoPatrones.entities.Persona;
import com.sprint.KioscoPatrones.services.BaseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public abstract class BaseController<E extends Persona, ID> {

    // Cada controlador hijo nos dirá qué servicio usar
    protected abstract BaseService<E, ID> getService();

    // Y nos dirá los nombres de sus vistas
    protected abstract String getEntityName(); // ej: "proveedor"

    protected String getListView() {
        return "views/usuario/Menu/" + getEntityName() + "es/detalle";
    }

    protected String getFormView() {
        return "views/usuario/Menu/" + getEntityName() + "es/Form" + getEntityName().substring(0, 1).toUpperCase() + getEntityName().substring(1);
    }

    protected String getRedirectListView() {
        return "redirect:/usuario/" + getEntityName() + "/detalle";
    }


    @GetMapping("/detalle")
    public String mostrarLista(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        try {
            model.addAttribute(getEntityName() + "es", getService().findAllActivos());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return getListView();
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") ID id, HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";
        try {
            getService().deleteById(id);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return getRedirectListView();
    }

}