package com.sprint.ejercicio2bCliente.services;

import com.sprint.ejercicio2bCliente.DTO.PersonaBasicaDTO;
import com.sprint.ejercicio2bCliente.DTO.PersonaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PersonaService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:9000/api/v1/personas";


    public List<PersonaBasicaDTO> findAll() {
        ResponseEntity<PersonaBasicaDTO[]> response =
                restTemplate.getForEntity(API_URL, PersonaBasicaDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public PersonaDTO findById(Long id) {
        return restTemplate.getForObject(API_URL + "/" + id, PersonaDTO.class);
    }

    public PersonaDTO save(PersonaDTO persona) {
        return restTemplate.postForObject(API_URL, persona, PersonaDTO.class);
    }

    public void update(Long id, PersonaDTO persona) {
        restTemplate.put(API_URL + "/" + id, persona);
    }

    public void delete(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }
}
