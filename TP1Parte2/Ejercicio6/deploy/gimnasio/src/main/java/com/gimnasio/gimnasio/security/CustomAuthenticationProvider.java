package com.gimnasio.gimnasio.security;

import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import com.gimnasio.gimnasio.utils.HashForLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreUsuario(username);
        if (usuarioOpt.isEmpty()) {
            throw new BadCredentialsException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        String stored = usuario.getClave();

        // Si la clave almacenada tiene formato BCrypt (comienza con $2), usar passwordEncoder.matches
        if (stored != null && stored.startsWith("$2")) {
            if (passwordEncoder.matches(password, stored)) {
                List<GrantedAuthority> permisos = new ArrayList<>();
                permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));

                // establecer usuario en sesión para compatibilidad con controllers existentes
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("usuarioLogueado", usuario);

                return new UsernamePasswordAuthenticationToken(username, password, permisos);
            } else {
                throw new BadCredentialsException("Credenciales inválidas");
            }
        }

        // Si no es BCrypt, comparar con SHA-256 legacy
        String sha = HashForLogin.hashClave(password);
        if (sha.equals(stored)) {
            // Re-hashear con BCrypt y guardar
            String nueva = passwordEncoder.encode(password);
            usuario.setClave(nueva);
            usuarioRepository.save(usuario);

            List<GrantedAuthority> permisos = new ArrayList<>();
            permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));

            // establecer usuario en sesión para compatibilidad con controllers existentes
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuarioLogueado", usuario);

            return new UsernamePasswordAuthenticationToken(username, password, permisos);
        }

        throw new BadCredentialsException("Credenciales inválidas");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
