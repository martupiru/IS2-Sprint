package com.sprint.contactos.entities;

import com.sprint.contactos.entities.enums.TipoTelefono;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contacto_telefonico")
@Getter @Setter
public class ContactoTelefonico extends Contacto {

    private String telefono;

    @Enumerated(EnumType.STRING)
    private TipoTelefono tipoTelefono; // FIJO o CELULAR


    public String getTelefonoLimpio() { //Limpia
        if (telefono == null) return "";
        return telefono.replaceAll("[\\s-]", "");
    }

    public String getWhatsAppLink() { // Genera el link para Whatsapp Web
        if (tipoTelefono == TipoTelefono.CELULAR) {
            return "https://wa.me/" + getTelefonoLimpio();
        }
        return null;
    }

    public boolean isPuedeAbrirWhatsApp() {
        return tipoTelefono == TipoTelefono.CELULAR;
    }

}

