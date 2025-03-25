package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.InscripcionEvento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionEventoDTO {
    private Integer id;
    private Integer idCliente;
    private Integer idEvento;
    private LocalDateTime fechaInscripcion;

    public InscripcionEventoDTO(InscripcionEvento inscripcion) {
        this.id = inscripcion.getId();
        this.idCliente = inscripcion.getCliente().getId();
        this.idEvento = inscripcion.getEvento().getId();
        this.fechaInscripcion = inscripcion.getFechaInscripcion();
    }
}
