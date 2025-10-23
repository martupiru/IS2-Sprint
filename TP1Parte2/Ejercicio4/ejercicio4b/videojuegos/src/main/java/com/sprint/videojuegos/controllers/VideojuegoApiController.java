package com.sprint.videojuegos.controllers;

import com.sprint.videojuegos.entities.Videojuego;
import com.sprint.videojuegos.services.CategoriaService;
import com.sprint.videojuegos.services.EstudioService;
import com.sprint.videojuegos.services.VideojuegoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import jakarta.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/videojuegos")
public class VideojuegoApiController extends BaseControllerApi<Videojuego, Long>{
    @Autowired
    private VideojuegoService videojuegoService;


    public VideojuegoApiController(VideojuegoService videojuegoService) {
        super(videojuegoService);
    }


}
