package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.DTO.AutorDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroDigitalDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroFisicoDTO;
import com.sprint.ejercicio2bCliente.services.AutorService;
import com.sprint.ejercicio2bCliente.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/libro")
public class LibroControllerGeneric extends BaseController<LibroDTO, Long> {

    @Autowired
    private AutorService autorService;

    public LibroControllerGeneric(LibroService service) {
        super(service);
        initController(new LibroDTO(), "LIST LIBRO", "EDIT LIBRO");
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
