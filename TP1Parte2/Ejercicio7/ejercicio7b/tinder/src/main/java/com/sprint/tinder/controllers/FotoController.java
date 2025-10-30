package com.sprint.tinder.controllers;

import com.sprint.tinder.entities.Mascota;
import com.sprint.tinder.entities.Usuario;
import com.sprint.tinder.services.MascotaService;
import com.sprint.tinder.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class FotoController {
    @Autowired
    private UsuarioService usuarioServicio;
    @Autowired
    private MascotaService mascotaServicio;

    @GetMapping("/foto/usuario/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable String id) {
        try {
            Usuario usuario = usuarioServicio.buscarUsuario(id);
            if (usuario == null || usuario.getFoto() == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] foto = usuario.getFoto().getContenido();
            String mime = usuario.getFoto().getMime() != null ? usuario.getFoto().getMime() : "application/octet-stream";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(mime));

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/foto/mascota/{id}")
    public ResponseEntity<byte[]> fotoMascota(@PathVariable String id) {
        try {
            Mascota mascota = mascotaServicio.buscarMascota(id);
            if (mascota == null || mascota.getFoto() == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] foto = mascota.getFoto().getContenido();
            String mime = mascota.getFoto().getMime() != null ? mascota.getFoto().getMime() : "application/octet-stream";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(mime));

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, e);
            return ResponseEntity.notFound().build();
        }
    }
}
