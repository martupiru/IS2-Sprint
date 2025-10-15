package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.DTO.AutorDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroDigitalDTO;
import com.sprint.ejercicio2bCliente.services.AutorService;
import com.sprint.ejercicio2bCliente.services.LibroDigitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/librodigital")
public class LibroDigitalController extends BaseController<LibroDigitalDTO, Long> {

    @Autowired
    private AutorService autorService;

    public LibroDigitalController(LibroDigitalService service) {
        super(service);
        initController(new LibroDigitalDTO(), "LIBROS DIGITALES", "LIBRO DIGITAL");
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
