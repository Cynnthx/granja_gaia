package com.example.granja_gaia.dtos;

import com.example.granja_gaia.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDTO {
    private String nickname;
    private Rol rol;
    private String email;
    private String contrasena;
}
