package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Integer id;
    private LocalDateTime fecha;
    private double total;
    private String estado;
    private Integer idCliente;

    // Constructor de la clase PedidoDTO que toma un objeto Pedido como parámetro.
    // Este constructor inicializa los campos del DTO con los valores del objeto Pedido proporcionado.
    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        // Asigna la fecha del pedido al campo fecha del DTO.
        this.fecha = pedido.getFecha();
        this.total = pedido.getTotal();
        this.estado = pedido.getEstado();
        this.idCliente = pedido.getCliente().getId();
    }
}
