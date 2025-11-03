package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.ContactoCorreoElectronico;
import com.sprint.contactos.services.ContactoCorreoElectronicoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contactoCorreo")
public class ContactoCorreoElectronicoController extends BaseController<ContactoCorreoElectronico, String> {

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
}
