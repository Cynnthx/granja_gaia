package com.example.granja_gaia.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroProductoDTO {
    private Integer idCategoria;
    private double precioMin = 0;
    private double precioMax = Double.MAX_VALUE;
    private String orden = "novedades"; // "precio_asc", "precio_desc", "novedades", "popularidad"
    private Boolean soloPopulares = false; // Filtro para mostrar solo productos populares
}
