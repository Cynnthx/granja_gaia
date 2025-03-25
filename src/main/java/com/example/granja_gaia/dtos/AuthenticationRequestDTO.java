package com.example.granja_gaia.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {
    private String nickname;
    private String contrasena;

    // DTO para manejar las solicitudes de autenticación, contiene el nombre de usuario y la contraseña

}
