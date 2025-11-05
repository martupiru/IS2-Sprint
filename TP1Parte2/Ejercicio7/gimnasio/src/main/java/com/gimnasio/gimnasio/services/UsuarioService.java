package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Persona;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // usamos BCrypt

    //CREAR USUARIO
    @Transactional
    public Usuario crearUsuario(String nombreUsuario, String clave, RolUsuario rol, Persona persona) throws Exception {
        try {
            if (usuarioRepository.findByNombreUsuario(nombreUsuario).isPresent()) {
                throw new Exception("Ya existe un usuario con ese nombre");
            }

            Usuario user = new Usuario();
            user.setNombreUsuario(nombreUsuario);
            user.setClave(passwordEncoder.encode(clave)); // Encriptar con BCrypt
            user.setRol(rol);
            user.setPersona(persona);
            user.setEliminado(false);

            return usuarioRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al crear el usuario: " + e.getMessage());
        }
    }

    //MODIFICAR USUARIO
    @Transactional
    public void modificarUsuario(String idUsuario, String nombreUsuario, String clave, RolUsuario rol, Persona persona) throws Exception {
        try {
            Usuario user = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new Exception("Usuario no encontrado"));

            user.setNombreUsuario(nombreUsuario);
            if (clave != null && !clave.isEmpty()) {
                user.setClave(passwordEncoder.encode(clave)); // Solo si hay nueva clave
            }
            user.setRol(rol);
            user.setPersona(persona);

            usuarioRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al modificar usuario: " + e.getMessage());
        }
    }

    //ELIMINAR USUARIO
    @Transactional
    public void eliminarUsuario(String idUsuario) throws Exception {
        Usuario user = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        user.setEliminado(true);
        usuarioRepository.save(user);
    }

    //BUSCAR USUARIO
    public Usuario buscarUsuario(String idUsuario) throws Exception {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
    }

    //LISTAR
    @Transactional
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.findAllByEliminadoFalse();
    }

    //BUSCAR POR NOMBRE DE USUARIO
    public Usuario buscarUsuarioPorNombre(String nombreUsuario) throws Exception {
        return usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
    }

    //CAMBIAR CLAVE
    @Transactional
    public void modificarClave(String idUsuario, String nuevaClave) throws Exception {
        Usuario user = buscarUsuario(idUsuario);
        user.setClave(passwordEncoder.encode(nuevaClave));
        usuarioRepository.save(user);
    }

    //Obtener socio desde usuario (sin cambios)
    public Socio getSocio(Usuario usuario) throws Exception {
        if (usuario.getRol() != RolUsuario.SOCIO) {
            throw new Exception("El usuario no es un socio");
        }
        Persona persona = usuario.getPersona();
        if (persona instanceof Socio socio) {
            return socio;
        } else {
            throw new Exception("La persona asociada no es un socio");
        }
    }
}
