package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.DetallesPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallesPedidoDTO {
    private Integer id;
    private Integer idProducto;
    private String imagenUrl;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Double total; // (cantidad * precioUnitario)

    // Constructor desde la entidad DetallesPedido
    public DetallesPedidoDTO(DetallesPedido detalles) {
        this.id = detalles.getId();
        this.idProducto = detalles.getProducto().getId();
        this.imagenUrl = detalles.getProducto().getImagenUrl();
        this.nombreProducto = detalles.getProducto().getNombre();
        this.cantidad = detalles.getCantidad();
        this.precioUnitario = detalles.getPrecioUnitario();
        this.total = this.cantidad * this.precioUnitario;
    }
}
