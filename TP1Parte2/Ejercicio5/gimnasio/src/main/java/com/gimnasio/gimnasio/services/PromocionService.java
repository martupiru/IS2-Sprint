package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Promocion;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.TipoMensaje;
import com.gimnasio.gimnasio.repositories.PromocionRepository;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void crearPromocion(String idUsuario, String titulo, String texto, LocalDate fechaEnvioPromocion, long cantidadSociosEnviados) throws Exception{
        try {
            validar(titulo, texto, fechaEnvioPromocion, cantidadSociosEnviados);
            Promocion promo = new Promocion();
            Optional<Usuario> user = usuarioRepository.findById(idUsuario);
            if (user.isEmpty()) {
                throw new Exception("Usuario no encontrado");
            }
            Usuario usuarioActual = user.get();
            promo.setUsuario(usuarioActual);
            promo.setTitulo(titulo);
            promo.setTexto(texto);
            promo.setTipoMensaje(TipoMensaje.PROMOCION);
            promo.setEliminado(false);
            promo.setFechaEnvioPromocion(fechaEnvioPromocion);
            promo.setCantidadSociosEnviados(cantidadSociosEnviados);
            promocionRepository.save(promo);
        } catch (Exception e) {
            throw new Exception("Error al crear promoción: " + e.getMessage());
        }
    }

    public void validar (String titulo, String texto, LocalDate fechaEnvioPromocion, long cantidadSociosEnviados) throws Exception{
        if(titulo == null || titulo.trim().isEmpty()){
            throw new Exception("El título no puede estar vacío");
        }
        if(texto == null || texto.trim().isEmpty()){
            throw new Exception("El texto de la promoción no puede estar vacío");
        }
        if(fechaEnvioPromocion == null){
            throw new Exception("La fecha de enviacion es obligatorio");
        }
        if(cantidadSociosEnviados <= 0){
            throw new Exception("La cantidad de socios enviados debe ser mayor a 0");
        }
    }

    public void modificarPromocion(String id, String idUsuario, String titulo, String texto, LocalDate fechaEnvioPromocion, long cantidadSociosEnviados) throws Exception{
        try {
            validar(titulo, texto, fechaEnvioPromocion, cantidadSociosEnviados);
            Optional<Promocion> promo = promocionRepository.findById(id);
            if (promo.isPresent()) {
                Promocion promoActual = promo.get();
                Optional<Usuario> user = usuarioRepository.findById(idUsuario);
                if (user.isEmpty()) {
                    throw new Exception("Usuario no encontrado");
                }
                Usuario usuarioActual = user.get();
                promoActual.setUsuario(usuarioActual);
                promoActual.setTitulo(titulo);
                promoActual.setTexto(texto);
                promoActual.setTipoMensaje(TipoMensaje.PROMOCION);
                promoActual.setFechaEnvioPromocion(fechaEnvioPromocion);
                promoActual.setCantidadSociosEnviados(cantidadSociosEnviados);
                promocionRepository.save(promoActual);
            } else {
                throw new Exception("Promoción no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar promoción: " + e.getMessage());
        }
    }

    public void eliminarPromocion(String idUsuario) throws Exception{
        try {
            Promocion promo = buscarPromocion(idUsuario);
            promo.setEliminado(true);
            promocionRepository.save(promo);
        } catch (Exception e) {
            throw new Exception("Error al eliminar mensaje: " + e.getMessage());
        }
    }

    public Promocion buscarPromocion(String idUsuario) throws Exception{
        try {
            Optional<Promocion> promo = promocionRepository.findById(idUsuario);
            if (promo.isPresent()) {
                Promocion promoActual = promo.get();
                return promoActual;
            } else {
                throw new Exception("Promoción no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar promoción: " + e.getMessage());
        }
    }

    @Transactional
    public List<Promocion> listarMensajes() throws Exception {
        try {
            List<Promocion> entities = this.promocionRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Promocion> listarMensajesActivos() throws Exception {
        try {
            return this.promocionRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
