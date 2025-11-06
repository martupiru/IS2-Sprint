package com.gimnasio.gimnasio.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/socios")
public class SocioApiController {

    @GetMapping
    public ResponseEntity<?> listarSocios() {
        return ResponseEntity.ok(
                List.of(
                        Map.of("nombre", "Juan", "apellido", "Pérez"),
                        Map.of("nombre", "Ana", "apellido", "García")
                )
        );
    }
}
