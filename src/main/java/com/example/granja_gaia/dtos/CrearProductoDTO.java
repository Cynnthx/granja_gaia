package com.example.granja_gaia.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.Map;

@Data
public class CrearProductoDTO {
    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    @Positive
    private double precio;

    private String imagenUrl;

    @PositiveOrZero
    private int stock;

    @NotNull
    private Integer idCategoria; // Solo recibimos el ID

    private boolean esPopular;

    private String especificaciones;
}