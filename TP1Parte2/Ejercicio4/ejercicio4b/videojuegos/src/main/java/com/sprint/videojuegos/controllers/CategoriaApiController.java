package com.sprint.videojuegos.controllers;

import com.sprint.videojuegos.entities.Categoria;
import com.sprint.videojuegos.services.CategoriaService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/categorias")
public class CategoriaApiController extends BaseControllerApi<Categoria, Long> {
    public CategoriaApiController(CategoriaService categoriaService) { super(categoriaService); }
}
