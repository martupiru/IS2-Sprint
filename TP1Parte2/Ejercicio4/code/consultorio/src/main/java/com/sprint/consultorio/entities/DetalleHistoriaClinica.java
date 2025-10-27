package com.sprint.consultorio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "detalle_historia_clinica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleHistoriaClinica extends Base {
    private LocalDate fechaHistoria;
    private String detalleHistoria;

    @ManyToOne
    @JoinColumn(name = "fk_medico")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "fk_historia_clinica")
    private HistoriaClinica historiaClinica;

}
