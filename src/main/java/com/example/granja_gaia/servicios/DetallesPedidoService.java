package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.DetallesPedidoDTO;
import com.example.granja_gaia.dtos.TotalPedidoDTO;
import com.example.granja_gaia.modelos.DetallesPedido;
import com.example.granja_gaia.modelos.Pedido;
import com.example.granja_gaia.modelos.Producto;
import com.example.granja_gaia.repositorios.DetallesPedidoRepository;
import com.example.granja_gaia.repositorios.PedidoRepository;
import com.example.granja_gaia.repositorios.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DetallesPedidoService {

    private final DetallesPedidoRepository detallesPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    /**
     * Agregar un producto al carrito (detalles de pedido).
     */
    public DetallesPedidoDTO agregarProductoAlCarrito(Integer idPedido, Integer idProducto, int cantidad) {
        // Validar cantidad positiva
        if (cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a cero");
        }

        // Obtener el pedido y validar estado
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (!"pendiente".equals(pedido.getEstado())) {
            throw new RuntimeException("No se puede modificar un pedido que no está en estado 'pendiente'");
        }

        // Obtener el producto
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar si el producto ya está en el carrito
        Optional<DetallesPedido> detalleExistente = detallesPedidoRepository
                .findByPedidoIdAndProductoId(idPedido, idProducto);

        DetallesPedido detalle;
        if (detalleExistente.isPresent()) {
            detalle = detalleExistente.get();
            detalle.setCantidad(detalle.getCantidad() + cantidad);
            detalle.setPrecioUnitario(producto.getPrecio()); // Actualiza precio siempre
        } else {
            // Crear nuevo registro si no existe
            detalle = new DetallesPedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(producto.getPrecio());
        }

        // Guardar cambios
        DetallesPedido detallesGuardado = detallesPedidoRepository.save(detalle);

        // Actualizar el total del pedido (opcional)
        actualizarTotalPedido(idPedido);

        return new DetallesPedidoDTO(detallesGuardado);
    }

    // Método auxiliar para actualizar el total del pedido
    private void actualizarTotalPedido(Integer idPedido) {
        Float total = detallesPedidoRepository.obtenerTotalPedido(idPedido);
        pedidoRepository.findById(idPedido).ifPresent(pedido -> {
            pedido.setTotal(total != null ? total : 0.0);
            pedidoRepository.save(pedido);
        });
    }
    /**
     * Modificar la cantidad de un producto en el carrito.
     */
    public Optional<DetallesPedidoDTO> modificarCantidadProducto(Integer idDetalle, int nuevaCantidad) {
        return detallesPedidoRepository.findById(idDetalle)
                .map(detalle -> {
                    detalle.setCantidad(nuevaCantidad);
                    return new DetallesPedidoDTO(detallesPedidoRepository.save(detalle));
                });
    }

    /**
     * Eliminar un producto del carrito.
     */
    public void eliminarProductoDelCarrito(Integer idDetalle) {
        detallesPedidoRepository.deleteById(idDetalle);
    }

    /**
     * Obtener el total del pedido (suma de todos los productos en el carrito).
     */
    public TotalPedidoDTO calcularTotalPedido(Integer idPedido) {
        Float total = detallesPedidoRepository.obtenerTotalPedido(idPedido);
        if (total == null) total = 0.0f;

        // Redondeo a 2 decimales
        double totalRedondeado = Math.round(total * 100.0) / 100.0;

        return new TotalPedidoDTO(totalRedondeado);
    }

    /**
     * Obtener todos los productos en el carrito (detalles de pedido) de un pedido.
     */
    public List<DetallesPedidoDTO> obtenerDetallesPedido(Integer idPedido) {
        return detallesPedidoRepository.findByPedidoId(idPedido)
                .stream()
                .map(DetallesPedidoDTO::new)
                .collect(Collectors.toList());
    }

}