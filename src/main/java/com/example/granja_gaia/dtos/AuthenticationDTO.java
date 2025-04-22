package com.example.granja_gaia.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {

    //PARA RESPUESTAS DE LOGIN Y REGISTRO

    private String token;          // Token JWT
    private String mensaje;        // Mensaje de estado
    private Integer usuarioId;     // ID del usuario
    private String rol;            // "admin" o "cliente"
    private String email;          // Email del usuario
    private String nickname;       // Nombre de usuario
    private String nombreCompleto; // Nombre + apellido (solo clientes)

    // Métodos helpers
    public static AuthenticationDTO crearExito(String token, Integer id, String rol,
                                               String email, String nickname, String nombreCompleto) {
        return builder()
                .token(token)
                .mensaje("Éxito")
                .usuarioId(id)
                .rol(rol)
                .email(email)
                .nickname(nickname)
                .nombreCompleto(nombreCompleto)
                .build();
    }

    public static AuthenticationDTO crearError(String mensaje) {
        return builder()
                .mensaje(mensaje)
                .build();
    }
}