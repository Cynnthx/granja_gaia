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
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetallesPedido detallesPedido = new DetallesPedido();
        detallesPedido.setPedido(pedido);
        detallesPedido.setProducto(producto);
        detallesPedido.setCantidad(cantidad);
        detallesPedido.setPrecioUnitario(producto.getPrecio());

        DetallesPedido detallesGuardado = detallesPedidoRepository.save(detallesPedido);
        return new DetallesPedidoDTO(detallesGuardado);
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
        return new TotalPedidoDTO(total != null ? total : 0.0f);
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