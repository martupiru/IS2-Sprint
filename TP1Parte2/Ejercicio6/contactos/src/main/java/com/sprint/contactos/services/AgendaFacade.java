package com.sprint.contactos.services;

import com.sprint.contactos.entities.ContactoCorreoElectronico;
import com.sprint.contactos.entities.ContactoTelefonico;
import com.sprint.contactos.entities.enums.TipoContacto;
import com.sprint.contactos.entities.enums.TipoTelefono;
import com.sprint.contactos.entities.Empresa;
import com.sprint.contactos.entities.Persona;
import org.springframework.stereotype.Service;

@Service
public class AgendaFacade {

    private final PersonaService personaService;
    private final EmpresaService empresaService;
    private final ContactoCorreoElectronicoService correoService;
    private final ContactoTelefonicoService telefonoService;

    public AgendaFacade(PersonaService personaService,
                        EmpresaService empresaService,
                        ContactoCorreoElectronicoService correoService,
                        ContactoTelefonicoService telefonoService) {
        this.personaService = personaService;
        this.empresaService = empresaService;
        this.correoService = correoService;
        this.telefonoService = telefonoService;
    }

    //Registra una persona con sus contactos (teléfono y correo)
    public void registrarPersonaCompleta(String nombre,
                                         String apellido,
                                         Empresa empresa,
                                         String email,
                                         String telefono,
                                         String tipoTelefono,
                                         String tipoContacto) throws ErrorServiceException {

        // 1️⃣ Crear la persona
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        personaService.alta(persona);

        // 2️⃣ Crear contacto de correo
        ContactoCorreoElectronico cCorreo = new ContactoCorreoElectronico();
        cCorreo.setEmail(email);
        cCorreo.setPersona(persona);
        cCorreo.setEmpresa(empresa);
        cCorreo.setTipoContacto(TipoContacto.valueOf(tipoContacto));
        correoService.alta(cCorreo);

        // 3️⃣ Crear contacto telefónico
        ContactoTelefonico cTelefono = new ContactoTelefonico();
        cTelefono.setTelefono(telefono);
        cTelefono.setTipoTelefono(TipoTelefono.valueOf(tipoTelefono));
        cTelefono.setTipoContacto(TipoContacto.valueOf(tipoContacto));
        cTelefono.setPersona(persona);
        cTelefono.setEmpresa(empresa);
        telefonoService.alta(cTelefono);
    }
}
