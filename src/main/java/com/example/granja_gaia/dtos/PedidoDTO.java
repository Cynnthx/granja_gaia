package com.example.granja_gaia.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoDTO {
    private Integer id;
    private LocalDateTime fecha;
    private String estado;
    private Double total;
    private Integer usuarioId;
    private String nombreUsuario;

}
