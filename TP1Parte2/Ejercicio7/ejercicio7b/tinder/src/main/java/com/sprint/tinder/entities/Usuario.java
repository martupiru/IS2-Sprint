package com.sprint.tinder.entities;

import com.sprint.tinder.enumerations.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"mail"})})
@Entity
@Builder
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private String nombre;
    private String apellido;
    @Column(nullable=false)
    private String mail;
    private String clave;
    @ManyToOne
    private Zona zona;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;

    @OneToOne
    private Foto foto;

    private boolean eliminado;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Implementación de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + (role != null ? role.name() : "USER")));
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return baja == null && !eliminado;
    }
}