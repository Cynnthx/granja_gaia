package com.example.granja_gaia.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDataDTO {
    private String username;
    private String rol;
    private long fecha_creacion;
    private long fecha_expiracion;
}
