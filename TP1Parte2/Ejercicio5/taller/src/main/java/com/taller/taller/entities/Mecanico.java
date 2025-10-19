package com.taller.taller.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mecanicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mecanico extends Persona {

    private String legajo;

    @OneToMany(mappedBy = "mecanico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialArreglo> historial = new ArrayList<>();

    @OneToMany(mappedBy = "mecanico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> usuarios = new ArrayList<>();

}

