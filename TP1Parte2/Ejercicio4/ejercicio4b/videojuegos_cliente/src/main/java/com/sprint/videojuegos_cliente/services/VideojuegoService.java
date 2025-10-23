package com.sprint.videojuegos_cliente.services;

import com.sprint.videojuegos_cliente.DTO.VideojuegoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Service
public class VideojuegoService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:9000/api/v1/videojuegos";


    public List<VideojuegoDTO> findAll() {
        ResponseEntity<VideojuegoDTO[]> response =
                restTemplate.getForEntity(API_URL, VideojuegoDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public VideojuegoDTO findById(Long id) {
        return restTemplate.getForObject(API_URL + "/" + id, VideojuegoDTO.class);
    }

    public VideojuegoDTO save(VideojuegoDTO videojuego) {
        return restTemplate.postForObject(API_URL, videojuego, VideojuegoDTO.class);
    }

    public void update(Long id, VideojuegoDTO videojuego) {
        restTemplate.put(API_URL + "/" + id, videojuego);
    }

    public void delete(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }
}