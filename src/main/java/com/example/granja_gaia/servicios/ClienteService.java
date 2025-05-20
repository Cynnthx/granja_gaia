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
import com.example.granja_gaia.repositorios.UsuarioRepository;
import jakarta.validation.Valid;
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
    private final UsuarioRepository usuarioRepository;


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

//    // Actualizar un cliente existente
//    @Transactional
//    public Optional<ClienteDTO> updateCliente(Integer id, ClienteDTO clienteDTO) {
//        return clienteRepository.findById(id)
//                .map(clienteExistente -> {
//                    // Actualizar solo los campos permitidos
//                    clienteExistente.setNombre(clienteDTO.getNombre());
//                    clienteExistente.setApellidos(clienteDTO.getApellidos());
//                    clienteExistente.setDni(clienteDTO.getDni());
//                    clienteExistente.setFotoPerfil(clienteDTO.getFotoPerfil());
//                    clienteExistente.setDireccion(clienteDTO.getDireccion());
//                    clienteExistente.setTelefono(clienteDTO.getTelefono());
//
//                    Cliente clienteActualizado = clienteRepository.save(clienteExistente);
//                    return convertToDto(clienteActualizado);
//                });
//    }
    // Eliminar un cliente
    @Transactional
    public void deleteCliente(Integer id) {
        clienteRepository.findById(id).ifPresent(cliente -> {
            usuarioService.deleteCliente(cliente.getUsuario().getId());
            clienteRepository.delete(cliente);
        });
    }

    /**
     * Este método edita un perfil de cliente existente
     *
     * @param clienteId
     * @param dto
     * @return ClienteDTO
     * @throws Exception
     */
    public ClienteDTO updateCliente(Integer clienteId, @Valid ClienteDTO dto) throws Exception {

        // Buscar al cliente por su ID
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new Exception("Cliente no encontrado"));


        // Validar el DNI
        if (dto.getDni() != null && dto.getDni().length() != 9) {
            throw new Exception("El DNI introducido no es válido");
        }

        // Actualizar los datos del cliente
        if (dto.getDni() != null) {
            cliente.setDni(dto.getDni());
        }
        if (dto.getFotoPerfil() != null) {
            cliente.setFotoPerfil(dto.getFotoPerfil());
        }
        if (dto.getDireccion() != null) {
            cliente.setDireccion(dto.getDireccion());
        }
        if (dto.getTelefono() != null) {
            cliente.setTelefono(dto.getTelefono());
        }


        // Guardar los cambios en el cliente y el usuario
        usuarioRepository.save(cliente.getUsuario());
        Cliente clienteGuardado = clienteRepository.save(cliente);

        // Crear y devolver el ClienteDTO actualizado
        return new ClienteDTO(clienteGuardado);
    }


//    // Obtener perfil completo del cliente
//    public ClienteDTO getClientePerfil(Integer id) {
//        Cliente cliente = clienteRepository.findByUsuarioId(id)
//                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
//        return convertToDto(cliente);
//    }
//

}