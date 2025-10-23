package com.sprint.videojuegos_cliente.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private float precio;
    private short stock;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaLanzamiento;
    private EstudioDTO estudio;
    private CategoriaDTO categoria;

}
