package com.sprint.ejercicio2b.controllers;

import com.sprint.ejercicio2b.entities.Libro;
import com.sprint.ejercicio2b.services.FileStorageService;
import com.sprint.ejercicio2b.services.LibroServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/libros")
public class LibroController extends BaseControllerImpl<Libro, LibroServiceImpl> {

    @Autowired
    private FileStorageService fileStorageService;

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

    // subir libro con PDF
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveWithFile(
            @RequestParam("titulo") String titulo,
            @RequestParam("fecha") int fecha,
            @RequestParam("genero") String genero,
            @RequestParam("paginas") int paginas,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "autores", required = false) String autoresJson) {
        try {
            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setFecha(fecha);
            libro.setGenero(genero);
            libro.setPaginas(paginas);

            // Guardar archivo pdf
            if (file != null && !file.isEmpty()) {
                String rutaArchivo = fileStorageService.storeFile(file, titulo);
                libro.setRutaArchivo(rutaArchivo);
            }
            return ResponseEntity.status(HttpStatus.OK).body(servicio.save(libro));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // descargar PDF
    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> downloadPdf(@PathVariable Long id) {
        try {
            Libro libro = servicio.findById(id);
            if (libro.getRutaArchivo() == null || libro.getRutaArchivo().isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Path filePath = Path.of(libro.getRutaArchivo());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Libro libro = servicio.findById(id);
            // Eliminar el pdf si existe
            if (libro.getRutaArchivo() != null && !libro.getRutaArchivo().isEmpty()) {
                fileStorageService.deleteFile(libro.getRutaArchivo());
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"deleted\":\"" + servicio.delete(id) + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
