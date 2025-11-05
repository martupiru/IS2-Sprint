package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.ContactoCorreoElectronico;
import com.sprint.contactos.entities.ContactoTelefonico;
import com.sprint.contactos.services.ContactoTelefonicoService;
import com.sprint.contactos.services.EmpresaService;
import com.sprint.contactos.services.ErrorServiceException;
import com.sprint.contactos.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contactoTelefono")
public class ContactoTelefonicoController extends BaseController<ContactoTelefonico, String> {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private PersonaService personaService;

    public ContactoTelefonicoController(ContactoTelefonicoService service) {
        super(service);
        initController(
                new ContactoTelefonico(),
                "Listado de Contactos Telefónicos",
                "ContactoTelefónico"
        );
        this.nameEntityLower = "contactoTelefono";
        this.redirectList = "redirect:/contacto/listar";
    }

    @Override
    protected void preAlta() throws ErrorServiceException {
        model.addAttribute("item", new ContactoTelefonico());
        model.addAttribute("empresas", empresaService.listarActivos());
        model.addAttribute("personas", personaService.listarActivos());
    }

    @Override
    protected void preModificacion() throws ErrorServiceException {
        model.addAttribute("empresas", empresaService.listarActivos());
        model.addAttribute("personas", personaService.listarActivos());
    }
}
