package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.ContactoTelefonico;
import com.sprint.contactos.services.ContactoTelefonicoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contactoTelefono")
public class ContactoTelefonicoController extends BaseController<ContactoTelefonico, String> {

    public ContactoTelefonicoController(ContactoTelefonicoService service) {
        super(service);
        initController(
                new ContactoTelefonico(),
                "Listado de Contactos Telefónicos",
                "ContactoTelefónico"
        );
        this.nameEntityLower = "contactoTelefono";
        this.redirectList = "redirect:/contactoTelefono/listar";
    }
}
