package com.sprint.videojuegos.controllers;

import com.sprint.videojuegos.entities.Estudio;
import com.sprint.videojuegos.services.EstudioService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/estudios")
public class EstudioApiController extends BaseControllerApi<Estudio, Long> {
    public EstudioApiController(EstudioService estudioService) { super(estudioService); }
}