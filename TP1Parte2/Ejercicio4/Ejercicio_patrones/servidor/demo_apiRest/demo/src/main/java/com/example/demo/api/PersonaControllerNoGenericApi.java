package com.example.demo.api;

import com.example.demo.entities.Persona;
import com.example.demo.services.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/personas")
public class PersonaControllerNoGenericApi {
    private PersonaService personaService;

    public PersonaControllerNoGenericApi(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.listarActivos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/crear")
    public  ResponseEntity<?> create(@RequestBody Persona persona) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(personaService.alta(persona));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
