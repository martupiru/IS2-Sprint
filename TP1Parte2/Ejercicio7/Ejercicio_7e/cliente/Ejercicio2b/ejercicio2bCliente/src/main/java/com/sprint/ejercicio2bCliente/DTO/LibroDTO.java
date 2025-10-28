package com.sprint.ejercicio2bCliente.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class LibroDTO implements Identifiable<Long> {
    private Long id;
    private String titulo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private String genero;
    private int paginas;
    private List<AutorDTO> autores;
}
