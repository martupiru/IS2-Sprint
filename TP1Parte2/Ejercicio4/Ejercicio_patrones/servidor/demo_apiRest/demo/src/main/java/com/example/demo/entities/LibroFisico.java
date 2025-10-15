package com.example.demo.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;



@Entity
@DiscriminatorValue("FISICO")
@Getter
@Setter
public class LibroFisico extends Libro {
    private String ubicacionEstante;
}
