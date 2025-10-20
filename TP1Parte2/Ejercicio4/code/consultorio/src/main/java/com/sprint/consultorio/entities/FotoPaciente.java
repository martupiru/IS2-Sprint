package com.sprint.consultorio.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "fotoPaciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FotoPaciente extends Base {
    private String nombre;
    private String mime;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] contenido;
}
