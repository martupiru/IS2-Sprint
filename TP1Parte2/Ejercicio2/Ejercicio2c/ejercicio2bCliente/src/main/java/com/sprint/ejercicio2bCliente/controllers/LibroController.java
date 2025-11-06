package com.sprint.ejercicio2bCliente.controllers;

import com.sprint.ejercicio2bCliente.DTO.AutorBasicoDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroBasicoDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroFormDTO;
import com.sprint.ejercicio2bCliente.services.AutorService;
import com.sprint.ejercicio2bCliente.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (libro.getRutaArchivo() != null && !libro.getRutaArchivo().isEmpty()) {
            model.addAttribute("pdfUrl", libroService.getPdfUrl(id));
        }
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
    public String guardar(
            @ModelAttribute LibroFormDTO libroForm,
            @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile,
            RedirectAttributes redirectAttributes) {
        try {
            // Convertir LibroFormDTO a LibroDTO
            LibroDTO libro = new LibroDTO();
            libro.setTitulo(libroForm.getTitulo());
            libro.setFecha(libroForm.getFecha());
            libro.setGenero(libroForm.getGenero());
            libro.setPaginas(libroForm.getPaginas());
            libro.setAutores(new java.util.ArrayList<>());
            if (pdfFile != null && !pdfFile.isEmpty()) {
                if (pdfFile.getSize() > 50 * 1024 * 1024) {
                    redirectAttributes.addFlashAttribute("error",
                            "El archivo es demasiado grande. Máximo 50MB.");
                    return "redirect:/libros/nuevo";
                }
                libroService.saveWithFile(libro, pdfFile.getBytes(), pdfFile.getOriginalFilename());
            } else {
                libroService.saveWithFile(libro, null, null);
            }
            redirectAttributes.addFlashAttribute("success", "Libro guardado exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al guardar el libro: " + e.getMessage());
            return "redirect:/libros/nuevo";
        }
        return "redirect:/libros";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        libroService.delete(id);
        return "redirect:/libros";
    }
}
