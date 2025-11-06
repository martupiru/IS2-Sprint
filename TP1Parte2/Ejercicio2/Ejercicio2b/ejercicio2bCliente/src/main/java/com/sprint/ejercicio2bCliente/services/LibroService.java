package com.sprint.ejercicio2bCliente.services;

import com.sprint.ejercicio2bCliente.DTO.LibroBasicoDTO;
import com.sprint.ejercicio2bCliente.DTO.LibroDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class LibroService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:9000/api/v1/libros";

    public List<LibroBasicoDTO> findAll() {
        try {
            ResponseEntity<LibroBasicoDTO[]> response =
                    restTemplate.getForEntity(API_URL, LibroBasicoDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener libros: " + e.getMessage());
        }
    }

    public LibroDTO findById(Long id) {
        try {
            return restTemplate.getForObject(API_URL + "/" + id, LibroDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener libro: " + e.getMessage());
        }
    }

    public LibroDTO save(LibroDTO libro) {
        try {
            return restTemplate.postForObject(API_URL, libro, LibroDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar libro: " + e.getMessage());
        }
    }

    public void update(Long id, LibroDTO libro) {
        try {
            restTemplate.put(API_URL + "/" + id, libro);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar libro: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            restTemplate.delete(API_URL + "/" + id);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar libro: " + e.getMessage());
        }
    }

    public List<LibroBasicoDTO> searchByTitulo(String titulo) {
        try {
            ResponseEntity<LibroBasicoDTO[]> response =
                    restTemplate.getForEntity(API_URL + "/search/titulo?titulo=" + titulo, LibroBasicoDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar libros: " + e.getMessage());
        }
    }

    public List<LibroBasicoDTO> searchByGenero(String genero) {
        try {
            ResponseEntity<LibroBasicoDTO[]> response =
                    restTemplate.getForEntity(API_URL + "/search/genero?genero=" + genero, LibroBasicoDTO[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar libros por género: " + e.getMessage());
        }
    }
}
