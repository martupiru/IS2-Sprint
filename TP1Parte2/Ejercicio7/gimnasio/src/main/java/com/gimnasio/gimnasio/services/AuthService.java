package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import com.gimnasio.gimnasio.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Map<String, Object> login(String nombreUsuario, String password) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(nombreUsuario, password)
        );

        Usuario user = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        String token = jwtService.generateToken(user);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("rol", user.getRol().name());
        return response;
    }
}
