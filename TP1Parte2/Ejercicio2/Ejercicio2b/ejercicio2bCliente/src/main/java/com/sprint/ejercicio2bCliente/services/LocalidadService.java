package com.sprint.ejercicio2bCliente.services;

import com.sprint.ejercicio2bCliente.DTO.LocalidadBasicaDTO;
import com.sprint.ejercicio2bCliente.DTO.LocalidadDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class LocalidadService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:9000/api/v1/localidades";

    public List<LocalidadBasicaDTO> findAll() {
        try {
            ResponseEntity<LocalidadBasicaDTO[]> response =
                    restTemplate.getForEntity(API_URL, LocalidadBasicaDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener localidades: " + e.getMessage());
        }
    }

    public LocalidadDTO findById(Long id) {
        try {
            return restTemplate.getForObject(API_URL + "/" + id, LocalidadDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener localidad: " + e.getMessage());
        }
    }

    public LocalidadDTO save(LocalidadDTO localidad) {
        try {
            return restTemplate.postForObject(API_URL, localidad, LocalidadDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar localidad: " + e.getMessage());
        }
    }

    public void update(Long id, LocalidadDTO localidad) {
        try {
            restTemplate.put(API_URL + "/" + id, localidad);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar localidad: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            restTemplate.delete(API_URL + "/" + id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar localidad: " + e.getMessage());
        }
    }
}
