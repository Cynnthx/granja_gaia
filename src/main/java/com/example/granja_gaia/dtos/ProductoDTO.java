package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String imagenUrl;
    private String especificaciones;
    private int stock;
    private Integer idCategoria;

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.imagenUrl = producto.getImagenUrl();
        this.especificaciones = producto.getEspecificaciones();
        this.stock = producto.getStock();
        this.idCategoria = producto.getCategoria().getId();
    }
}
