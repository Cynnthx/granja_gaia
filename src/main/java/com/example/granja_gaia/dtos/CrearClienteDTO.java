package com.example.granja_gaia.dtos;

import com.example.granja_gaia.enums.Rol;
import com.example.granja_gaia.modelos.Cliente;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearClienteDTO {

    // Datos del Cliente
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 150, message = "Los apellidos no pueden exceder 150 caracteres")
    private String apellidos;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "DNI no válido (8 números + 1 letra)")
    private String dni;

    @Size(max = 255, message = "La URL de la foto no puede exceder 255 caracteres")
    private String fotoPerfil;

    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;

    @Pattern(regexp = "^[+0-9\\s]{9,15}$", message = "Teléfono no válido (9-15 dígitos)")
    private String telefono;

    // Datos del Usuario
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;

    @NotBlank(message = "El nickname es obligatorio")
    @Size(min = 4, max = 20, message = "Nickname debe tener entre 4 y 20 caracteres")
    private String nickname;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 60, message = "La contraseña debe tener entre 8 y 60 caracteres")
    private String contrasena;

    // Campo Rol con valor por defecto
    @NotNull(message = "El rol es obligatorio")
    private Rol rol = Rol.cliente; // Valor por defecto para nuevos clientes

    // Método para convertir a entidad Cliente
    public Cliente toEntity() {
        return Cliente.builder()
                .nombre(this.nombre.trim())
                .apellidos(this.apellidos.trim())
                .dni(this.dni.trim().toUpperCase()) // Normaliza el DNI
                .fotoPerfil(this.fotoPerfil != null ? this.fotoPerfil.trim() : null)
                .direccion(this.direccion != null ? this.direccion.trim() : null)
                .telefono(this.telefono != null ? this.telefono.trim() : null)
                .build();
    }
}