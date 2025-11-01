package com.is.biblioteca.business.logic.service;

import com.is.biblioteca.business.domain.entity.Usuario;
import com.is.biblioteca.business.domain.enumeration.Rol;
import com.is.biblioteca.business.persistence.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // extrae la informacion del usuario de Google
        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub"); // ID único de Google
        String pictureUrl = oAuth2User.getAttribute("picture");
        Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(email);

        if (usuario == null) {
            //crear nuevo usuario OAuth2
            usuario = new Usuario();
            usuario.setId(UUID.randomUUID().toString());
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(null); // Sin contraseña para OAuth2
            usuario.setRol(Rol.USER);
            usuario.setProvider("GOOGLE");
            usuario.setProviderId(googleId);
            usuario.setPictureUrl(pictureUrl);
            usuario.setEliminado(false);

            usuarioRepository.save(usuario);
        } else {
            // Usuario existente
            if (usuario.getProvider() == null || usuario.getProvider().equals("LOCAL")) {
                // Vincular con google si el usuario se había registrado local antes
                usuario.setProvider("GOOGLE");
                usuario.setProviderId(googleId);
            }
            // Actualizar imagen de perfil
            if (pictureUrl != null && !pictureUrl.equals(usuario.getPictureUrl())) {
                usuario.setPictureUrl(pictureUrl);
            }
            usuarioRepository.save(usuario);
        }
        // Guardar en sesion HTTP
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usuariosession", usuario);
        // Crea los roles para Spring Security
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }
}