package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.ContactoCorreoElectronico;
import com.sprint.contactos.services.ContactoCorreoElectronicoService;
import com.sprint.contactos.services.EmpresaService;
import com.sprint.contactos.services.ErrorServiceException;
import com.sprint.contactos.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contactoCorreo")
public class ContactoCorreoElectronicoController extends BaseController<ContactoCorreoElectronico, String> {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private PersonaService personaService;

    public ContactoCorreoElectronicoController(ContactoCorreoElectronicoService service) {
        super(service);
        initController(
                new ContactoCorreoElectronico(),
                "Listado de Contactos por Correo",
                "ContactoCorreo"
        );
        this.nameEntityLower = "contactoCorreo";
        this.redirectList = "redirect:/contacto/listar";
    }

    @Override
    protected void preAlta() throws ErrorServiceException {
        model.addAttribute("item", new ContactoCorreoElectronico());
        model.addAttribute("empresas", empresaService.listarActivos());
        model.addAttribute("personas", personaService.listarActivos());
    }

    @Override
    protected void preModificacion() throws ErrorServiceException {
        model.addAttribute("empresas", empresaService.listarActivos());
        model.addAttribute("personas", personaService.listarActivos());
    }
}
