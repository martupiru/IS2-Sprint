package com.sprint.ejercicio2b.controllers;

import com.sprint.ejercicio2b.entities.Domicilio;
import com.sprint.ejercicio2b.services.DomicilioServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/domicilios")
public class DomicilioController extends BaseControllerImpl<Domicilio, DomicilioServiceImpl> {

    @GetMapping("/search/calle")
    public ResponseEntity<?> searchByCalle(@RequestParam String calle) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByCalle(calle));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/search/calle/paged")
    public ResponseEntity<?> searchByCalle(@RequestParam String calle, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByCalle(calle, pageable));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/search/localidad")
    public ResponseEntity<?> searchByLocalidad(@RequestParam String localidad) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByLocalidad(localidad));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/search/localidad/paged")
    public ResponseEntity<?> searchByLocalidad(@RequestParam String localidad, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByLocalidad(localidad, pageable));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
