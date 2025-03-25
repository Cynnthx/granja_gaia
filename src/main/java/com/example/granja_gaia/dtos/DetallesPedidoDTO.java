package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.DetallesPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallesPedidoDTO {
    private Integer id;
    private Integer idProducto;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double total; // (cantidad * precioUnitario)

    // Constructor desde la entidad DetallesPedido
    public DetallesPedidoDTO(DetallesPedido detalles) {
        this.id = detalles.getId();
        this.idProducto = detalles.getProducto().getId();
        this.nombreProducto = detalles.getProducto().getNombre();
        this.cantidad = detalles.getCantidad();
        this.precioUnitario = detalles.getPrecioUnitario();
        this.total = this.cantidad * this.precioUnitario;
    }
}
