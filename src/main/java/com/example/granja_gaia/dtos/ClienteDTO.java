package com.example.granja_gaia.dtos;

import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.modelos.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String fotoPerfil;
    private String direccion;
    private String telefono;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    // Constructor desde entidad Cliente
    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.apellidos = cliente.getApellidos();
        this.dni = cliente.getDni();
        this.fotoPerfil = cliente.getFotoPerfil();
        this.direccion = cliente.getDireccion();
        this.telefono = cliente.getTelefono();
        this.usuario = cliente.getUsuario();
    }
}