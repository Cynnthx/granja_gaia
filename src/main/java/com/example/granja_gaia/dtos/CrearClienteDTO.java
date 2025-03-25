package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.modelos.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearClienteDTO {
    private String nombre;
    private String apellidos;
    private String dni;
    private String fotoPerfil;
    private String direccion;
    private String telefono;
    private Integer usuarioId;
    private Usuario usuario;

    // Datos del Usuario (para creación)
    private String email;
    private String nickname;
    private String contrasena;

    //Método para convertir a entidad Cliente
    public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setNombre(this.nombre);
        cliente.setApellidos(this.apellidos);
        cliente.setDni(this.dni);
        cliente.setFotoPerfil(this.fotoPerfil);
        cliente.setDireccion(this.direccion);
        cliente.setTelefono(this.telefono);
        return cliente;
    }



}
