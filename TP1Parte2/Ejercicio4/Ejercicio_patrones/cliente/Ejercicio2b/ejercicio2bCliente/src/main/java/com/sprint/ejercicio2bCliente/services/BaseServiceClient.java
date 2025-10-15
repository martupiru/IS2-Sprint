package com.sprint.ejercicio2bCliente.services;


import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public abstract class BaseServiceClient<T, ID> {

    protected final RestTemplate restTemplate = new RestTemplate();
    protected final String apiUrl;
    protected final Class<T> dtoClass;

    protected BaseServiceClient(String apiUrl, Class<T> dtoClass) {
        this.apiUrl = apiUrl;
        this.dtoClass = dtoClass;
    }

    public List<T> listarActivos() {
            ResponseEntity<T[]> response = restTemplate.getForEntity(apiUrl, getArrayType());
            return Arrays.asList(response.getBody());
    }

    @SuppressWarnings("unchecked")
    private Class<T[]> getArrayType() {
        return (Class<T[]>) java.lang.reflect.Array.newInstance(dtoClass, 0).getClass();
    }

    public T obtener(ID id) {
        return restTemplate.getForObject(apiUrl + "/" + id, dtoClass);
    }

    public T alta(T dto) {
        return restTemplate.postForObject(apiUrl, dto, dtoClass);
    }

    public void modificar(ID id, T dto) {
        restTemplate.put(apiUrl + "/" + id, dto);
    }

    public void bajaLogica(ID id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}

