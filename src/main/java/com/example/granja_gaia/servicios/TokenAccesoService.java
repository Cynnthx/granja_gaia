package com.example.granja_gaia.servicios;

import com.example.granja_gaia.modelos.TokenAcceso;
import com.example.granja_gaia.modelos.Usuario;
import com.example.granja_gaia.repositorios.TokenAccesoRepository;
import com.example.granja_gaia.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenAccesoService {
    @Autowired
    private TokenAccesoRepository tokenAccesoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public TokenAcceso crearTokenParaUsuario(int usuarioId) {
        // Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear el token
        TokenAcceso token = TokenAcceso.builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
                .fechaCreacion(LocalDateTime.now())
                .fechaExpiracion(LocalDateTime.now().plusHours(1))
                .usuario(usuario)
                .build();

        // Guardar el token en la base de datos
        return tokenAccesoRepository.save(token);
    }
}
