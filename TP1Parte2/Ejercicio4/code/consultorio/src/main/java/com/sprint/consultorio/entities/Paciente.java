package com.sprint.consultorio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente extends Base {
    private String nombre;
    private String apellido;
    private String documento;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_foto_paciente")
    private FotoPaciente foto;
}
