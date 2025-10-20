package com.sprint.consultorio.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "historia_clinica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriaClinica extends Base {

    @OneToOne
    @JoinColumn(name = "fk_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "fk_usuario_creador")
    private Usuario usuarioCreador;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_historia_clinica")
    @Builder.Default
    private List<DetalleHistoriaClinica> detalles = new ArrayList<>();
}
