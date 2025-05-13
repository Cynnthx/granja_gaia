package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.ClienteDTO;
import com.example.granja_gaia.dtos.CrearClienteDTO;
import com.example.granja_gaia.dtos.CrearUsuarioDTO;
import com.example.granja_gaia.dtos.FotoDTO;
import com.example.granja_gaia.enums.Rol;
import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.modelos.Usuario;
import com.example.granja_gaia.repositorios.ClienteRepository;
import com.example.granja_gaia.repositorios.DetallesPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioService usuarioService;
    private final DetallesPedidoRepository detallesPedidoRepositorio;
    private final PasswordEncoder passwordEncoder;



    // Método para obtener la imagen de perfil del cliente
//    public FotoDTO getFotoById(Integer id) {
//        Cliente cliente = clienteRepository.findByUsuarioId(id)
//                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el usuario ID: " + id));
//        return new FotoDTO(cliente.getFotoPerfil());
//    }

    // Obtener todos los clientes
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ClienteDTO convertToDto(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellidos(cliente.getApellidos());
        dto.setDni(cliente.getDni());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setFotoPerfil(cliente.getFotoPerfil());
        return dto;
    }

    // Buscar cliente por ID
    public Optional<ClienteDTO> findById(Integer id) {
        return clienteRepository.findById(id)
                .map(this::convertToDto);
    }


    //Buscar cliente a partir de usuarioId a Id
    public ClienteDTO findClienteIdByUsuarioId(Integer usuarioId) {


        Cliente c  =clienteRepository.findTopByUsuarioId(usuarioId).orElse(null);

        if (c != null) {
            return convertToDto(c);
        } else {
            return null;
        }

    }

    // Crear un nuevo cliente y su usuario asociado
    @Transactional
    public ClienteDTO createCliente(CrearClienteDTO clienteDTO) {
// 1. Crear DTO para el usuario
        CrearUsuarioDTO usuarioDTO = new CrearUsuarioDTO();
        usuarioDTO.setEmail(clienteDTO.getEmail());
        usuarioDTO.setNickname(clienteDTO.getNickname());
        usuarioDTO.setContrasena(clienteDTO.getContrasena());
        usuarioDTO.setRol(clienteDTO.getRol());

        // 2. Crear y guardar el usuario a través del servicio
        Usuario usuario = usuarioService.crearUsuario(usuarioDTO);

        // 3. Crear y guardar el cliente
        Cliente cliente = clienteDTO.toEntity();
        cliente.setUsuario(usuario);

        return convertToDto(clienteRepository.save(cliente));
    }

    // Actualizar un cliente existente
    @Transactional
    public Optional<ClienteDTO> updateCliente(Integer id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    // Actualizar solo los campos permitidos
                    clienteExistente.setNombre(clienteDTO.getNombre());
                    clienteExistente.setApellidos(clienteDTO.getApellidos());
                    clienteExistente.setDni(clienteDTO.getDni());
                    clienteExistente.setFotoPerfil(clienteDTO.getFotoPerfil());
                    clienteExistente.setDireccion(clienteDTO.getDireccion());
                    clienteExistente.setTelefono(clienteDTO.getTelefono());

                    Cliente clienteActualizado = clienteRepository.save(clienteExistente);
                    return convertToDto(clienteActualizado);
                });
    }
    // Eliminar un cliente
    @Transactional
    public void deleteCliente(Integer id) {
        clienteRepository.findById(id).ifPresent(cliente -> {
            usuarioService.deleteCliente(cliente.getUsuario().getId());
            clienteRepository.delete(cliente);
        });
    }

//    // Obtener perfil completo del cliente
//    public ClienteDTO getClientePerfil(Integer id) {
//        Cliente cliente = clienteRepository.findByUsuarioId(id)
//                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
//        return convertToDto(cliente);
//    }
//
//    // Actualizar perfil del cliente
//    @Transactional
//    public Optional<ClienteDTO> actualizarClientePerfil(Integer id, CrearClienteDTO clientePerfilDTO) {
//        return clienteRepository.findByUsuarioId(id)
//                .map(cliente -> {
//                    // Actualizar datos del cliente
//                    cliente.setFotoPerfil(clientePerfilDTO.getFotoPerfil());
//                    cliente.setDni(clientePerfilDTO.getDni());
//                    cliente.setNombre(clientePerfilDTO.getNombre());
//                    cliente.setApellidos(clientePerfilDTO.getApellidos());
//                    cliente.setDireccion(clientePerfilDTO.getDireccion());
//                    cliente.setTelefono(clientePerfilDTO.getTelefono());
//
//                    // Actualizar datos del usuario
//                    Usuario usuario = cliente.getUsuario();
//                    usuario.setEmail(clientePerfilDTO.getEmail());
//                    usuario.setNickname(clientePerfilDTO.getNickname());
//
//                    // Solo actualizar contraseña si se proporcionó una nueva
//                    if (clientePerfilDTO.getContrasena() != null && !clientePerfilDTO.getContrasena().isEmpty()) {
//                        usuario.setContrasena(passwordEncoder.encode(clientePerfilDTO.getContrasena()));
//                    }
//
//                    usuarioService.saveUsuario(usuario);
//                    return convertToDto(clienteRepository.save(cliente));
//                });
//    }
}