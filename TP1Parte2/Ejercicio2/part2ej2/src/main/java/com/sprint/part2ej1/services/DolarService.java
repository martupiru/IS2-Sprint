package com.sprint.part2ej1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sprint.part2ej1.models.dolar;

import java.util.Arrays;
import java.util.List;

@Service
public class DolarService {

    public static final String URL_DOLAR = "https://dolarapi.com/v1/dolares/blue";

    @Autowired
    private RestTemplate restTemplate;

    public List<dolar> obtenerDatosDolar() {
        dolar post = restTemplate.getForObject(URL_DOLAR, dolar.class);
        return Arrays.asList(post);
    }

//metodo para retornar compra, venta y fecha
public List<String> obtenerCompraVentaFecha() {
    dolar post = restTemplate.getForObject(URL_DOLAR, dolar.class);
    List<String> newList = new java.util.ArrayList<>();
    if (post != null) {
        newList.add(Double.toString(post.getCompra()));
        newList.add(Double.toString(post.getVenta()));
        newList.add(post.getFechaActualizacion());
    }
    return newList;
}



}
