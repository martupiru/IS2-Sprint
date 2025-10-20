package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Mensaje;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.TipoMensaje;
import com.gimnasio.gimnasio.repositories.MensajeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    public void crearMensaje(String idUsuario, String titulo, String texto, TipoMensaje tipoMensaje) throws Exception{
        try {
            Usuario usuario = usuarioService.buscarUsuario(idUsuario);
            Mensaje mensaje = new Mensaje();
            mensaje.setTitulo(titulo);
            mensaje.setTexto(texto);
            mensaje.setTipoMensaje(tipoMensaje);
            mensaje.setUsuario(usuario);
            mensaje.setEliminado(false);
            mensajeRepository.save(mensaje);
        } catch (Exception e) {
            throw new Exception("Error al crear mensaje: " + e.getMessage());
        }
    }

    public void modificarMensaje(String idUsuario, String titulo, String texto, TipoMensaje tipoMensaje) throws Exception{
        try {
            Optional<Mensaje> mensaje = mensajeRepository.findById(idUsuario);
            if (mensaje.isPresent()) {
                Usuario usuario = usuarioService.buscarUsuario(idUsuario);
                Mensaje mensajeActual = mensaje.get();
                mensajeActual.setTitulo(titulo);
                mensajeActual.setTexto(texto);
                mensajeActual.setTipoMensaje(tipoMensaje);
                mensajeActual.setUsuario(usuario);
                mensajeRepository.save(mensajeActual);
            } else {
                throw new Exception("Mensaje no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar mensaje: " + e.getMessage());
        }
    }

    public void eliminarMensaje(String idUsuario) throws Exception{
        try {
            Mensaje mensaje = buscarMensaje(idUsuario);
            mensaje.setEliminado(true);
            mensajeRepository.save(mensaje);
        } catch (Exception e) {
            throw new Exception("Error al eliminar mensaje: " + e.getMessage());
        }
    }

    public Mensaje buscarMensaje(String idUsuario) throws Exception{
        try {
            Optional<Mensaje> mensaje = mensajeRepository.findById(idUsuario);
            if (mensaje.isPresent()) {
                Mensaje mensajeActual = mensaje.get();
                return mensajeActual;
            } else {
                throw new Exception("Mensaje no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar mensaje: " + e.getMessage());
        }
    }

    @Transactional
    public List<Mensaje> listarMensajes() throws Exception {
        try {
            List<Mensaje> entities = this.mensajeRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Mensaje> listarMensajesActivos() throws Exception {
        try {
            return this.mensajeRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Mensaje crearMensajeObj(String idUsuario, String titulo, String texto, TipoMensaje tipoMensaje) throws Exception{
        try {
            Usuario usuario = usuarioService.buscarUsuario(idUsuario);
            Mensaje mensaje = new Mensaje();
            mensaje.setTitulo(titulo);
            mensaje.setTexto(texto);
            mensaje.setTipoMensaje(tipoMensaje);
            mensaje.setUsuario(usuario);
            mensaje.setEliminado(false);
            mensajeRepository.save(mensaje);
            return mensaje;
        } catch (Exception e) {
            throw new Exception("Error al crear mensaje: " + e.getMessage());
        }
    }

    @Transactional
    public void enviarMensaje(String idMensaje) throws Exception {
        try {
            Optional<Mensaje> mensajeOpt = mensajeRepository.findById(idMensaje);
            if (mensajeOpt.isEmpty()) {
                throw new Exception("Mensaje no encontrado");
            }
            Mensaje mensaje = mensajeOpt.get();
            Usuario usuario = mensaje.getUsuario();
            if (usuario == null || usuario.getNombreUsuario() == null) {
                throw new Exception("El mensaje no tiene un usuario válido");
            }
            // Enviar mail con el service de email
            emailService.enviarEmail(usuario.getNombreUsuario(), mensaje.getTitulo(), mensaje.getTexto());
        } catch (Exception e) {
            throw new Exception("Error al enviar mensaje: " + e.getMessage());
        }
    }

}
