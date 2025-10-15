package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.DTO.AutorDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroFisicoDTO;
import com.sprint.ejercicio2bCliente.services.AutorService;
import com.sprint.ejercicio2bCliente.services.LibroFisicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/librofisico")
public class LibroFisicoController extends BaseController<LibroFisicoDTO, Long> {

    @Autowired
    private AutorService autorService;

    public LibroFisicoController(LibroFisicoService service) {
        super(service);
        initController(new LibroFisicoDTO(), "LIBROS FÍSICOS", "LIBRO FÍSICO");
    }

    @Override
    protected void preAlta() {
        List<AutorDTO> autores = autorService.listarActivos();
        model.addAttribute("autoresList", autores);
    }

    @Override
    protected void preModificacion() {
        List<AutorDTO> autores = autorService.listarActivos();
        model.addAttribute("autoresList", autores);
    }
}