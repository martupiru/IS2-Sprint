package com.sprint.part2ej1.services;

import com.sprint.part2ej1.entities.Usuario;

import com.sprint.part2ej1.repositories.ProveedorRepository;
import com.sprint.part2ej1.repositories.UsuarioRepository;
import com.sprint.part2ej1.utils.HashForLogin;
import jakarta.transaction.Transactional;
import com.sprint.part2ej1.entities.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private ProveedorRepository proveedorRepository;


    @Transactional
    public void crearUsuario(String cuenta, String clave) throws Exception{
        try {
            Usuario user = new Usuario();
            user.setCuenta(cuenta);
            String claveHash = HashForLogin.hashClave(clave); //Encriptar clave
            user.setClave(claveHash);
//            user.setPersona(persona);
            usuarioRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al crear el usuario: " + e.getMessage());
        }
    }


    @Transactional
    public void modificarUsuario(String idUsuario, String cuenta, String clave) throws Exception{
        try {
            Optional<Usuario> user = usuarioRepository.findById(idUsuario);
            if (user.isPresent()) {
                Usuario userAct = user.get();
                userAct.setCuenta(cuenta);
                String claveHash = HashForLogin.hashClave(clave); //Encriptar clave
                userAct.setClave(claveHash);
//                userAct.setPersona(persona);
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
}
