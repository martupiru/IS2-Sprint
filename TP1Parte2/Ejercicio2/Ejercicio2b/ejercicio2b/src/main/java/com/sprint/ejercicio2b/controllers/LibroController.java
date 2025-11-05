package com.sprint.ejercicio2b.controllers;

import com.sprint.ejercicio2b.entities.Libro;
import com.sprint.ejercicio2b.services.LibroServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/libros")
public class LibroController extends BaseControllerImpl<Libro, LibroServiceImpl> {

    @GetMapping("/search/titulo")
    public ResponseEntity<?> searchByTitulo(@RequestParam String titulo) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByTitulo(titulo));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/search/titulo/paged")
    public ResponseEntity<?> searchByTitulo(@RequestParam String titulo, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByTitulo(titulo, pageable));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/search/genero")
    public ResponseEntity<?> searchByGenero(@RequestParam String genero) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByGenero(genero));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/search/genero/paged")
    public ResponseEntity<?> searchByGenero(@RequestParam String genero, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByGenero(genero, pageable));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/search/autor")
    public ResponseEntity<?> searchByAutor(@RequestParam String autor) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByAutor(autor));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/search/autor/paged")
    public ResponseEntity<?> searchByAutor(@RequestParam String autor, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByAutor(autor, pageable));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
