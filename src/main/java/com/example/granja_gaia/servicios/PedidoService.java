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
     * Obtener el historial de pedidos de un cliente.
     */
    public List<PedidoDTO> obtenerHistorialPedidos(Integer idCliente) {
        return pedidoRepository.findByClienteId(idCliente)
                .stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Crear un nuevo pedido con estado 'pendiente'.
     */
    public PedidoDTO crearPedido(Integer idCliente) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setTotal(0.0); // Inicialmente en 0, se actualizará con los detalles del pedido
        pedido.setEstado("pendiente");
        pedido.setCliente(cliente);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return new PedidoDTO(pedidoGuardado);
    }

    /**
     * Editar un pedido (solo si está en estado 'pendiente').
     */
    public Optional<PedidoDTO> editarPedido(Integer idPedido, Pedido pedidoDetalles) {
        return pedidoRepository.findById(idPedido)
                .filter(p -> p.getEstado().equals("pendiente")) // Solo permite edición si está pendiente
                .map(pedido -> {
                    pedido.setTotal(pedidoDetalles.getTotal());
                    return new PedidoDTO(pedidoRepository.save(pedido));
                });
    }

    /**
     * Eliminar un pedido (solo si está en estado 'pendiente').
     */
    public void eliminarPedido(Integer idPedido) {
        pedidoRepository.findById(idPedido)
                .filter(p -> p.getEstado().equals("pendiente")) // Solo permite eliminar si está pendiente
                .ifPresent(pedidoRepository::delete);
    }

    /**
     * Cambiar el estado de un pedido a 'enviado'.
     */
    public Optional<PedidoDTO> marcarPedidoComoEnviado(Integer idPedido) {
        return pedidoRepository.findById(idPedido)
                .filter(p -> p.getEstado().equals("pendiente")) // Solo permite cambiar estado si es pendiente
                .map(pedido -> {
                    pedido.setEstado("enviado");
                    return new PedidoDTO(pedidoRepository.save(pedido));
                });
    }
}
