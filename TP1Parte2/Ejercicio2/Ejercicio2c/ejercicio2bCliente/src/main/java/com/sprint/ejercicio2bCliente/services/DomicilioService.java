package com.sprint.ejercicio2bCliente.services;

import com.sprint.ejercicio2bCliente.DTO.DomicilioBasicoDTO;
import com.sprint.ejercicio2bCliente.DTO.DomicilioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DomicilioService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:9000/api/v1/domicilios";

    public List<DomicilioBasicoDTO> findAll() {
        try {
            ResponseEntity<DomicilioBasicoDTO[]> response =
                    restTemplate.getForEntity(API_URL, DomicilioBasicoDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener domicilios: " + e.getMessage());
        }
    }

    public DomicilioDTO findById(Long id) {
        try {
            return restTemplate.getForObject(API_URL + "/" + id, DomicilioDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener domicilio: " + e.getMessage());
        }
    }

    public DomicilioDTO save(DomicilioDTO domicilio) {
        try {
            return restTemplate.postForObject(API_URL, domicilio, DomicilioDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar domicilio: " + e.getMessage());
        }
    }

    public void update(Long id, DomicilioDTO domicilio) {
        try {
            restTemplate.put(API_URL + "/" + id, domicilio);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar domicilio: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            restTemplate.delete(API_URL + "/" + id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar domicilio: " + e.getMessage());
        }
    }
}
