package com.sprint.carrito.controllers;

import com.sprint.carrito.entities.Imagen;
import com.sprint.carrito.repositories.ImagenRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/img")
public class ImagenController {

    private final ImagenRepository imagenRepository;

    public ImagenController(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> servirImagen(@PathVariable String id) throws IOException {
        Optional<Imagen> opt = imagenRepository.findById(id);

        if (opt.isPresent() && opt.get().getContenido() != null) {
            Imagen imagen = opt.get();
            String mime = imagen.getMime() != null ? imagen.getMime() : "application/octet-stream";
            MediaType mediaType = MediaType.parseMediaType(mime);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic())
                    .body(imagen.getContenido());
        }

        ClassPathResource placeholder = new ClassPathResource("static/images/no-image.png");
        if (placeholder.exists()) {
            byte[] bytes = StreamUtils.copyToByteArray(placeholder.getInputStream());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic())
                    .body(bytes);
        }

        return ResponseEntity.notFound().build();
    }
}