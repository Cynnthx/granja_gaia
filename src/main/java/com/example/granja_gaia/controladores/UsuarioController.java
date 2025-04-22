package com.example.granja_gaia.controladores;

import com.example.granja_gaia.dtos.*;
import com.example.granja_gaia.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Registro público para clientes
    @PostMapping("/registro/cliente")
    public ResponseEntity<AuthenticationDTO> registrarCliente(@RequestBody CrearClienteDTO registroDTO) {
        return ResponseEntity.ok(usuarioService.registrarCliente(registroDTO));
    }

    // Registro para administradores
    @PostMapping("/registro/admin")
    public ResponseEntity<AuthenticationDTO> registrarAdmin(@RequestBody AdminDTO registroDTO) {
        return ResponseEntity.ok(usuarioService.registrarAdmin(registroDTO));
    }

    // Login público
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(usuarioService.login(loginRequest));
    }

    // Obtener perfil del usuario autenticado
    @GetMapping("/perfil")
    public ResponseEntity<CrearClienteDTO> obtenerPerfil() {
        return ResponseEntity.ok(usuarioService.obtenerPerfilUsuario());
    }

    // Actualizar perfil
    @PutMapping("/perfil")
    public ResponseEntity<CrearClienteDTO> actualizarPerfil(@RequestBody ActualizarPerfilRequest perfilRequest) {
        return ResponseEntity.ok(usuarioService.actualizarPerfil(perfilRequest));
    }

}