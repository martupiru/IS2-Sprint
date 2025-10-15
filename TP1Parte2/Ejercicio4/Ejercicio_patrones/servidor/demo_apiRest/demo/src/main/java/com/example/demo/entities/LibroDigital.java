package com.example.demo.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("DIGITAL")
@Getter
@Setter
public class LibroDigital extends Libro {

    private String urlDescarga;

}
