package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.Resena;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResenaDTO {
    private Integer id;
    private Integer idCliente;
    private Integer idEvento;
    private String resena;
    private Integer valoracion;
    private LocalDateTime fecha;

    // Constructor para convertir de entidad a DTO
    public ResenaDTO(Resena resena) {
        this.id = resena.getId();
        this.idCliente = resena.getCliente().getId();
        // Asigna el ID del evento al campo idEvento del DTO.
        // Si el evento asociado a la reseña no es nulo, obtiene el ID del evento.
        // Si el evento es nulo, asigna null al campo idEvento.
        this.idEvento = (resena.getEvento() != null) ? resena.getEvento().getId() : null;
        this.resena = resena.getResena();
        this.valoracion = resena.getValoracion();
        this.fecha = resena.getFecha();
    }
}
