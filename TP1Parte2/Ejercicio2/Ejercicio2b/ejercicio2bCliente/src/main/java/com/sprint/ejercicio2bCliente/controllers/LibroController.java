package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.DTO.AutorBasicoDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroBasicoDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroDTO;
import com.sprint.ejercicio2bCliente.services.AutorService;
import com.sprint.ejercicio2bCliente.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String buscar) {
        List<LibroBasicoDTO> libros;
        if (buscar != null && !buscar.isEmpty()) {
            libros = libroService.searchByTitulo(buscar);
        } else {
            libros = libroService.findAll();
        }
        model.addAttribute("libros", libros);
        return "libros";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        LibroDTO libro = libroService.findById(id);
        model.addAttribute("libro", libro);
        return "libro-detalle";
    }

    @GetMapping("/nuevo")
    public String nuevoLibro(Model model) {
        model.addAttribute("libro", new LibroDTO());
        List<AutorBasicoDTO> autores = autorService.findAll();
        model.addAttribute("autores", autores);
        return "libro-form";
    }

    @GetMapping("/editar/{id}")
    public String editarLibro(@PathVariable Long id, Model model) {
        LibroDTO libro = libroService.findById(id);
        model.addAttribute("libro", libro);
        List<AutorBasicoDTO> autores = autorService.findAll();
        model.addAttribute("autores", autores);
        return "libro-form";
    }

    @PostMapping
    public String guardar(@ModelAttribute LibroDTO libro) {
        if (libro.getTitulo() != null && !libro.getTitulo().isEmpty()) {
            libroService.save(libro);
        }
        return "redirect:/libros";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        libroService.delete(id);
        return "redirect:/libros";
    }
}
