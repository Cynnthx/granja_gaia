package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.Producto;
import lombok.Data;
import java.util.Date;

@Data
public class ProductoDetalleDTO extends ProductoDTO {
    private String especificaciones;
    private Date fechaCreacion;
    private Boolean esPopular;

    public ProductoDetalleDTO(Producto producto) {
        super(producto); // Llama al constructor de ProductoDTO
        this.especificaciones = producto.getEspecificaciones();
        this.fechaCreacion = producto.getFechaCreacion();
        this.esPopular = producto.getEsPopular();
    }
}