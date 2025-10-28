package com.sprint.ejercicio2bCliente.services;


import com.sprint.ejercicio2bCliente.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public abstract class BaseServiceClient<T, ID> {

    protected final RestTemplate restTemplate = new RestTemplate();
    protected final String apiUrl;
    protected final Class<T> dtoClass;

    @Autowired
    protected AuthService authService; // 🔹 para acceder al token JWT

    protected BaseServiceClient(String apiUrl, Class<T> dtoClass) {
        this.apiUrl = apiUrl;
        this.dtoClass = dtoClass;
    }

    public List<T> listarActivos() {
        HttpEntity<Void> entity = new HttpEntity<>(authService.authHeaders());

        ResponseEntity<T[]> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                getArrayType()
        );
        return Arrays.asList(response.getBody());
    }

    @SuppressWarnings("unchecked")
    private Class<T[]> getArrayType() {
        return (Class<T[]>) java.lang.reflect.Array.newInstance(dtoClass, 0).getClass();
    }

    public T obtener(ID id) {
        HttpEntity<Void> entity = new HttpEntity<>(authService.authHeaders());
        ResponseEntity<T> response = restTemplate.exchange(
                apiUrl + "/" + id,
                HttpMethod.GET,
                entity,
                dtoClass
        );
        return response.getBody();
    }

    public T alta(T dto) {
        HttpEntity<T> entity = new HttpEntity<>(dto, authService.authHeaders());
        ResponseEntity<T> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                dtoClass
        );
        return response.getBody();
    }

    public void modificar(ID id, T dto) {
        HttpEntity<T> entity = new HttpEntity<>(dto, authService.authHeaders());
        restTemplate.exchange(apiUrl + "/" + id, HttpMethod.PUT, entity, Void.class);
    }

    public void bajaLogica(ID id) {
        HttpEntity<Void> entity = new HttpEntity<>(authService.authHeaders());
        restTemplate.exchange(apiUrl + "/" + id, HttpMethod.DELETE, entity, Void.class);
    }
}

