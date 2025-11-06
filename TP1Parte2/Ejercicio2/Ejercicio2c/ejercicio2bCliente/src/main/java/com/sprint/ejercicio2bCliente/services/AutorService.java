package com.sprint.ejercicio2bCliente.services;

import com.sprint.ejercicio2bCliente.DTO.AutorBasicoDTO;
import com.sprint.ejercicio2bCliente.DTO.AutorLibroDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AutorService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:9000/api/v1/autores";

    public List<AutorBasicoDTO> findAll() {
        try {
            ResponseEntity<AutorBasicoDTO[]> response =
                    restTemplate.getForEntity(API_URL, AutorBasicoDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener autores: " + e.getMessage());
        }
    }

    public AutorLibroDTO findById(Long id) {
        try {
            return restTemplate.getForObject(API_URL + "/" + id, AutorLibroDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener autor: " + e.getMessage());
        }
    }

    public AutorLibroDTO save(AutorLibroDTO autor) {
        try {
            return restTemplate.postForObject(API_URL, autor, AutorLibroDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar autor: " + e.getMessage());
        }
    }

    public void update(Long id, AutorLibroDTO autor) {
        try {
            restTemplate.put(API_URL + "/" + id, autor);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar autor: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            restTemplate.delete(API_URL + "/" + id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar autor: " + e.getMessage());
        }
    }
}
