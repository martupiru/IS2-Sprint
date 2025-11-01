package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.ContactoCorreoElectronico;
import com.sprint.contactos.entities.ContactoTelefonico;
import com.sprint.contactos.services.ContactoCorreoElectronicoService;
import com.sprint.contactos.services.ContactoTelefonicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

    private final ContactoCorreoElectronicoService correoService;
    private final ContactoTelefonicoService telefonoService;

    public ContactoController(ContactoCorreoElectronicoService correoService, ContactoTelefonicoService telefonoService) {
        this.correoService = correoService;
        this.telefonoService = telefonoService;
    }

    @GetMapping("/listar")
    public String listarContactos(Model model) {
        List<ContactoCorreoElectronico> correos = correoService.findAll();
        List<ContactoTelefonico> telefonos = telefonoService.findAll();
        model.addAttribute("correos", correos);
        model.addAttribute("telefonos", telefonos);
        return "view/lContacto"; // plantilla general de listado
    }
}
