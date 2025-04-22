package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.PedidoDTO;
import com.example.granja_gaia.modelos.Pedido;
import com.example.granja_gaia.modelos.Cliente;
import com.example.granja_gaia.repositorios.PedidoRepository;
import com.example.granja_gaia.repositorios.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    /**
     * Obtener todos los pedidos
     */
    public List<PedidoDTO> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener pedidos por cliente
     * @param clienteId
     * @return
     */
    public List<PedidoDTO> obtenerPedidosPorCliente(Integer clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener pedidos por rangos de fecha
     * @param fechaInicio
     * @param fechaFin
     * @return
     */
    public List<PedidoDTO> obtenerPedidosPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return pedidoRepository.findByFechaBetween(fechaInicio, fechaFin).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un pedido por su id
     * @param id
     * @return
     */
    public PedidoDTO obtenerPedidoPorId(Integer id) {
        return pedidoRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    /**
     * Crear un nuevo pedido
     * @param clienteId
     * @return
     */
    public PedidoDTO crearPedido(Integer clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setTotal(0.0);
        pedido.setEstado("pendiente");
        pedido.setCliente(cliente);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return convertToDto(pedidoGuardado);
    }

    /**
     * Actualizar el estado de un pedido
     * @param id
     * @param nuevoEstado
     * @return
     */
    public PedidoDTO actualizarEstadoPedido(Integer id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(nuevoEstado);
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return convertToDto(pedidoActualizado);
    }


    /**
     * Eliminar un pedido (solo si está en estado 'pendiente').
     */
    public void eliminarPedido(Integer idPedido) {
        pedidoRepository.findById(idPedido)
                .filter(p -> p.getEstado().equals("pendiente")) // Solo permite eliminar si está pendiente
                .ifPresent(pedidoRepository::delete);
    }


    // Método privado para convertir Pedido a DTO
    private PedidoDTO convertToDto(Pedido pedido) {
        return PedidoDTO.builder()
                .id(pedido.getId())
                .fecha(pedido.getFecha())
                .estado(pedido.getEstado())
                .total(pedido.getTotal())
                .usuarioId(pedido.getCliente().getId())
                .nombreUsuario(pedido.getCliente().getNombre())
                .build();
    }
}
