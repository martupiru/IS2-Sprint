package com.gimnasio.gimnasio.entities;

import com.gimnasio.gimnasio.enumerations.TipoEmpleado;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empleados")
public class Empleado extends Persona {

    @NotNull(message = "El campo es obligatorio")
    @Enumerated(EnumType.STRING)
    private TipoEmpleado tipoEmpleado;

}