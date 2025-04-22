package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.*;
import com.example.granja_gaia.enums.Rol;
import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.modelos.Usuario;
import com.example.granja_gaia.modelos.Pedido;
import com.example.granja_gaia.repositorios.ClienteRepository;
import com.example.granja_gaia.repositorios.UsuarioRepository;
import com.example.granja_gaia.repositorios.PedidoRepository;
import com.example.granja_gaia.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Registro de cliente con validación de email y nickname únicos
    public AuthenticationDTO registrarCliente(CrearClienteDTO registroDTO) {
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            return AuthenticationDTO.crearError("El email ya está en uso");
        }
        if (usuarioRepository.existsByNickname(registroDTO.getNickname())) {
            return AuthenticationDTO.crearError("El nickname ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(registroDTO.getEmail());
        usuario.setNickname(registroDTO.getNickname());
        usuario.setContrasena(passwordEncoder.encode(registroDTO.getContrasena()));
        usuario.setRol(Rol.cliente);

        usuarioRepository.save(usuario);

        Cliente cliente = registroDTO.toEntity();
        cliente.setUsuario(usuario);
        clienteRepository.save(cliente);

        // Generar token automáticamente al registrar
        String token = jwtService.generateToken(usuario);

        return AuthenticationDTO.crearExito(token, usuario.getId(), usuario.getRol().name(),
                usuario.getEmail(), usuario.getNickname(), cliente.getNombre() + " " + cliente.getApellidos());
    }

    // Registro de administrador con validación
    public AuthenticationDTO registrarAdmin(AdminDTO registroDTO) {
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            return AuthenticationDTO.crearError("El email ya está en uso");
        }
        if (usuarioRepository.existsByNickname(registroDTO.getNickname())) {
            return AuthenticationDTO.crearError("El nickname ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(registroDTO.getEmail());
        usuario.setNickname(registroDTO.getNickname());
        usuario.setContrasena(passwordEncoder.encode(registroDTO.getContrasena()));
        usuario.setRol(Rol.admin);

        usuarioRepository.save(usuario);

        // Generar token automáticamente al registrar
        String token = jwtService.generateToken(usuario);

        return AuthenticationDTO.crearExito(token, usuario.getId(), usuario.getRol().name(),
                usuario.getEmail(), usuario.getNickname(), null);
    }

    // Login de usuario mejorado
    public AuthenticationDTO login(LoginRequestDTO loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isEmpty() || !passwordEncoder.matches(loginRequest.getContrasena(), usuarioOpt.get().getContrasena())) {
            return AuthenticationDTO.crearError("Credenciales inválidas");
        }

        Usuario usuario = usuarioOpt.get();
        String token = jwtService.generateToken(usuario);

        String nombreCompleto = null;
        if (usuario.getRol() == Rol.cliente) {
            Optional<Cliente> clienteOpt = clienteRepository.findByUsuario(usuario);
            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                nombreCompleto = cliente.getNombre() + " " + cliente.getApellidos();
            }
        }

        return AuthenticationDTO.crearExito(token, usuario.getId(), usuario.getRol().name(),
                usuario.getEmail(), usuario.getNickname(), nombreCompleto);
    }

    // Obtener perfil del usuario autenticado
    public CrearClienteDTO obtenerPerfilUsuario() {
        Usuario usuario = obtenerUsuarioAutenticado();
        Cliente cliente = clienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Perfil de cliente no encontrado"));

        CrearClienteDTO perfilDTO = new CrearClienteDTO();

        // Datos del cliente
        perfilDTO.setNombre(cliente.getNombre());
        perfilDTO.setApellidos(cliente.getApellidos());
        perfilDTO.setDni(cliente.getDni());
        perfilDTO.setFotoPerfil(cliente.getFotoPerfil());
        perfilDTO.setDireccion(cliente.getDireccion());
        perfilDTO.setTelefono(cliente.getTelefono());

        // Datos del usuario
        perfilDTO.setEmail(usuario.getEmail());
        perfilDTO.setNickname(usuario.getNickname());

        // No establecemos contraseña por seguridad
        perfilDTO.setContrasena(null);

        return perfilDTO;
    }

    // Actualizar perfil con validación de autenticación
    @Transactional
    public CrearClienteDTO actualizarPerfil(ActualizarPerfilRequest perfilRequest) {
        // 1. Obtener usuario autenticado y su perfil de cliente
        Usuario usuario = obtenerUsuarioAutenticado();
        Cliente cliente = clienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Perfil de cliente no encontrado"));

        // 2. Actualizar datos del cliente
        cliente.setNombre(perfilRequest.getNombre());
        cliente.setApellidos(perfilRequest.getApellidos());
        cliente.setDireccion(perfilRequest.getDireccion());
        cliente.setTelefono(perfilRequest.getTelefono());
        cliente.setFotoPerfil(perfilRequest.getFotoPerfil());

        // 3. Guardar cambios
        Cliente clienteActualizado = clienteRepository.save(cliente);

        // 4. Retornar DTO actualizado
        CrearClienteDTO response = new CrearClienteDTO();
        response.setNombre(clienteActualizado.getNombre());
        response.setApellidos(clienteActualizado.getApellidos());
        response.setDni(clienteActualizado.getDni());
        response.setDireccion(clienteActualizado.getDireccion());
        response.setTelefono(clienteActualizado.getTelefono());
        response.setFotoPerfil(clienteActualizado.getFotoPerfil());
        response.setEmail(usuario.getEmail());
        response.setNickname(usuario.getNickname());

        return response;
    }

    // Guardar a Usuario
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Crear y guardar un nuevo usuario
    public Usuario crearUsuario(CrearUsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setNickname(usuarioDTO.getNickname());
        usuario.setContrasena(passwordEncoder.encode(usuarioDTO.getContrasena()));
        usuario.setRol(usuarioDTO.getRol());
        return usuarioRepository.save(usuario);
    }

    // Eliminar un cliente
    @Transactional
    public void deleteCliente(Integer id) {
        clienteRepository.findById(id).ifPresent(cliente -> {
            clienteRepository.delete(cliente);
        });
    }

    // Eliminar pedido solo si pertenece al usuario autenticado
    public void eliminarPedido(Integer idPedido) {
        Usuario usuario = obtenerUsuarioAutenticado();
        pedidoRepository.findById(idPedido)
                .filter(p -> p.getCliente().getUsuario().getId().equals(usuario.getId()))
                .ifPresentOrElse(pedidoRepository::delete, () -> {
                    throw new RuntimeException("No tienes permisos para eliminar este pedido");
                });
    }

    // Obtener usuario autenticado
    private Usuario obtenerUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nickname = auth.getName();
        return usuarioRepository.findTopByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuario no autenticado"));
    }

    // Actualizar usuario
    public void updateUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }


}