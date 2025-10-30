package com.sprint.tinder.auth;

import com.sprint.tinder.entities.Usuario;
import com.sprint.tinder.jwt.JwtService;
import com.sprint.tinder.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getMail(), request.getClave()));
        UserDetails user = usuarioRepository.findByMail(request.getMail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
                .mail(request.getMail())
                .clave(request.getClave())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .zona(request.getZona())
                .alta(request.getAlta())
                .baja(request.getBaja())
                .foto(request.getFoto())
                .eliminado(request.isEliminado())
                .build();
        usuarioRepository.save(user);
        return AuthResponse.builder()
                .token(JwtService.getToken(user))
                .build();
    }
}
