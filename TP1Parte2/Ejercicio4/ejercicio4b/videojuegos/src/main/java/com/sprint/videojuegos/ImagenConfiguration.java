package com.sprint.videojuegos;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ImagenConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads", "images");
        String uploadPathUri = uploadDir.toAbsolutePath().toUri().toString(); // -> file:/C:/.../uploads/images/

        // Log para depuración: ver en consola desde dónde se sirven las imágenes
        System.out.println(">> Static images will be served from: " + uploadPathUri);

        // Servimos primero desde la carpeta externa (uploads/images) y luego por compatibilidad desde classpath
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPathUri, "classpath:/static/images/");

        // Alias en español si lo necesitás
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations(uploadPathUri, "classpath:/static/images/");
    }

}