package com.sprint.tinder.auth;

import com.sprint.tinder.entities.Foto;
import com.sprint.tinder.entities.Zona;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String mail;
    private String clave;
    private String nombre;
    private String apellido;
    private Zona zona;
    private Date alta;
    private Date baja;
    private Foto foto;
    private boolean eliminado;
}
