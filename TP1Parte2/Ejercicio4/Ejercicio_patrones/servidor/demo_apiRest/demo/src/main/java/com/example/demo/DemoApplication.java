package com.example.demo;

import com.example.demo.entities.Persona;
import com.example.demo.services.PersonaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
        //crear personas
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Perez");
        persona.setDni(123);
        persona.setEliminado(true);

        //guardar
        PersonaService personaService = new PersonaService(null);
        try {
            personaService.alta(persona);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

	}

}
