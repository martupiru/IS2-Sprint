package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Persona;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.RolUsuario;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import com.gimnasio.gimnasio.utils.HashForLogin;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void crearUsuario(String nombreUsuario, String clave, RolUsuario rol, Persona persona) throws Exception{
        try {
            Usuario user = new Usuario();
            user.setNombreUsuario(nombreUsuario);
            String claveHash = HashForLogin.hashClave(clave); //Encriptar clave
            user.setClave(claveHash);
            user.setRol(rol);
            user.setPersona(persona);
            usuarioRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al crear el usuario: " + e.getMessage());
        }
    }


    @Transactional
    public void modificarUsuario(String idUsuario, String nombreUsuario, String clave, RolUsuario rol, Persona persona) throws Exception{
        try {
            Optional<Usuario> user = usuarioRepository.findById(idUsuario);
            if (user.isPresent()) {
                Usuario userAct = user.get();
                userAct.setNombreUsuario(nombreUsuario);
                String claveHash = HashForLogin.hashClave(clave); //Encriptar clave
                userAct.setClave(claveHash);
                userAct.setRol(rol);
                userAct.setPersona(persona);
                usuarioRepository.save(userAct);
            } else {
                throw new Exception("Usuario no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar usuario: " + e.getMessage());
        }
    }
    @Transactional
    public void eliminarUsuario(String idUsuario) throws Exception{
        try {
            Usuario user = buscarUsuario(idUsuario);
            user.setEliminado(true);
            usuarioRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al eliminar usuario: " + e.getMessage());
        }
    }

    public Usuario buscarUsuario(String idUsuario) throws Exception{
        try {
            Optional<Usuario> user = usuarioRepository.findById(idUsuario);
            if (user.isPresent()) {
                Usuario userActual = user.get();
                return userActual;
            } else {
                throw new Exception("Usuario no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar usuario: " + e.getMessage());
        }
    }
    @Transactional
    public List<Usuario> listarUsuarios() throws Exception {
        try {
            List<Usuario> entities = this.usuarioRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Usuario> listarUsuariosActivos() throws Exception {
        try {
            return this.usuarioRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Usuario buscarUsuarioPorNombre(String nombreUsuario) throws Exception {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new Exception("Debe indicar el usuario");
        }
        try {
            return usuarioRepository.findByNombreUsuario(nombreUsuario)
                    .orElseThrow(() -> new Exception("Usuario no encontrado"));
        } catch (Exception e) {
            throw new Exception("Error al buscar usuario por nombre: " + e.getMessage());
        }
    }

//    @Transactional
//    public Usuario login(String nombreUsuario, String clave) throws Exception {
//        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
//            throw new Exception("Debe indicar el usuario");
//        }
//        if (clave == null || clave.trim().isEmpty()) {
//            throw new Exception("Debe indicar la clave");
//        }
//
//        return usuarioRepository.login(nombreUsuario, clave)
//                .orElseThrow(() -> new Exception("Credenciales inválidas o usuario eliminado"));
//    }

    @Transactional
    public void modificarClave(String idUsuario, String nuevaClave) throws Exception {
        if (idUsuario == null || idUsuario.trim().isEmpty()) {
            throw new Exception("Debe indicar el ID del usuario");
        }
        if (nuevaClave == null || nuevaClave.trim().isEmpty()) {
            throw new Exception("Debe indicar la nueva clave");
        }
        try {
            Usuario user = buscarUsuario(idUsuario);
            String claveHash = HashForLogin.hashClave(nuevaClave); //Encriptar clave
            user.setClave(claveHash);
            usuarioRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al modificar clave: " + e.getMessage());
        }
    }

    public Optional<Usuario> login(String email, String password) {
        String claveHash = HashForLogin.hashClave(password);
        return usuarioRepository.login(email, claveHash);
    }

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
