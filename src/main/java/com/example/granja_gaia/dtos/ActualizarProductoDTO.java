package com.example.granja_gaia.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ActualizarProductoDTO {
    private String nombre;

    private String descripcion;

    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    private String imagenUrl;

    private String especificaciones;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private Integer idCategoria;

    private Boolean esPopular; // Permite marcar/desmarcar como popular
}