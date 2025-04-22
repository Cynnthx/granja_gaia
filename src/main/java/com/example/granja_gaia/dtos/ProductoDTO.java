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
    private int stock;
    private Integer idCategoria;
    private String nombreCategoria;
    private boolean esPopular; // Nuevo campo

    public ProductoDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.imagenUrl = producto.getImagenUrl();
        this.stock = producto.getStock();
        this.idCategoria = producto.getCategoria().getId();
        this.nombreCategoria = producto.getCategoria().getNombre();
        this.esPopular = producto.getEsPopular();
    }


}