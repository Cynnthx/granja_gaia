package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private Integer capacidad;
    private Double precio;

    public EventoDTO(Evento evento) {
        this.id = evento.getId();
        this.nombre = evento.getNombre();
        this.descripcion = evento.getDescripcion();
        this.fecha = evento.getFecha();
        this.capacidad = evento.getCapacidad();
        this.precio = evento.getPrecio();
    }
}
