package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.ClienteDTO;
import com.example.granja_gaia.dtos.CrearClienteDTO;
import com.example.granja_gaia.dtos.FotoDTO;
import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.modelos.Usuario;
import com.example.granja_gaia.repositorios.ClienteRepository;
import com.example.granja_gaia.repositorios.DetallesPedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioService usuarioService;
    private final DetallesPedidoRepository detallesPedidoRepositorio;

    // Método para obtener la imagen de perfil del cliente
    public FotoDTO getFotoById(Integer id) {
        Cliente cliente = clienteRepository.findByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado para el usuario ID: " + id));
        return new FotoDTO(cliente.getFotoPerfil());
    }

    // Obtener todos los clientes
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(ClienteDTO::new)
                .toList();
    }

    // Buscar cliente por ID
    public Optional<ClienteDTO> findById(Integer id) {
        return clienteRepository.findById(id)
                .map(ClienteDTO::new);
    }

    // Crear un nuevo cliente
    public ClienteDTO createCliente(Cliente cliente) {
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return new ClienteDTO(clienteGuardado);
    }

    // Actualizar un cliente existente
    public Optional<ClienteDTO> updateCliente(Integer id, Cliente clienteDetails) {
        return clienteRepository.findById(id)
                .map(existingCliente -> {
                    existingCliente.setNombre(clienteDetails.getNombre());
                    existingCliente.setApellidos(clienteDetails.getApellidos());
                    existingCliente.setDni(clienteDetails.getDni());
                    existingCliente.setFotoPerfil(clienteDetails.getFotoPerfil());
                    existingCliente.setDireccion(clienteDetails.getDireccion());
                    existingCliente.setTelefono(clienteDetails.getTelefono());
                    return new ClienteDTO(clienteRepository.save(existingCliente));
                });
    }

    // Eliminar un cliente
    public void deleteCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    // Obtener perfil completo del cliente
    public ClienteDTO getClientePerfil(Integer id) {
        Cliente cliente = clienteRepository.findByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Usuario usuario = cliente.getUsuario();

        return new ClienteDTO(cliente);

    }

    public Optional<ClienteDTO> actualizarClientePerfil(Integer id, CrearClienteDTO clientePerfilDTO) {
        return clienteRepository.findByUsuarioId(id)
                .map(cliente -> {
                    cliente.setFotoPerfil(clientePerfilDTO.getFotoPerfil());
                    cliente.setDni(clientePerfilDTO.getDni());
                    cliente.setNombre(clientePerfilDTO.getNombre());
                    cliente.setApellidos(clientePerfilDTO.getApellidos());
                    cliente.setDireccion(clientePerfilDTO.getDireccion());
                    cliente.setTelefono(clientePerfilDTO.getTelefono());

                    Usuario usuario = cliente.getUsuario();
                    usuario.setEmail(clientePerfilDTO.getUsuario().getEmail());
                    usuario.setNickname(clientePerfilDTO.getUsuario().getNickname());
                    usuarioService.updateUsuario(usuario);

                    return new ClienteDTO(clienteRepository.save(cliente));
                });
    }


}