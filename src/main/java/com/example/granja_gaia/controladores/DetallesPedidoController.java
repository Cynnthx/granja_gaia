package com.example.granja_gaia.controladores;

import com.example.granja_gaia.dtos.DetallesPedidoDTO;
import com.example.granja_gaia.dtos.TotalPedidoDTO;
import com.example.granja_gaia.servicios.DetallesPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class DetallesPedidoController {
    @Autowired
    private DetallesPedidoService detallesPedidoService;

    // 1. Agregar producto al carrito
    @PostMapping("/pedido/{idPedido}/producto/{idProducto}")
    public ResponseEntity<DetallesPedidoDTO> agregarProducto(
            @PathVariable Integer idPedido,
            @PathVariable Integer idProducto,
            @RequestParam int cantidad) {
        DetallesPedidoDTO detalle = detallesPedidoService.agregarProductoAlCarrito(idPedido, idProducto, cantidad);
        return ResponseEntity.ok(detalle);
    }

    // 2. Modificar cantidad de un producto
    @PutMapping("/detalle/{idDetalle}")
    public ResponseEntity<DetallesPedidoDTO> modificarCantidad(
            @PathVariable Integer idDetalle,
            @RequestParam int cantidad) {
        return detallesPedidoService.modificarCantidadProducto(idDetalle, cantidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Eliminar producto del carrito
    @DeleteMapping("/detalle/{idDetalle}")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable Integer idDetalle) {
        detallesPedidoService.eliminarProductoDelCarrito(idDetalle);
        return ResponseEntity.noContent().build();
    }

    // 4. Obtener todos los productos del carrito
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<DetallesPedidoDTO>> obtenerCarrito(
            @PathVariable Integer idPedido) {
        List<DetallesPedidoDTO> detalles = detallesPedidoService.obtenerDetallesPedido(idPedido);
        return ResponseEntity.ok(detalles);
    }

    // 5. Calcular total del carrito
    @GetMapping("/pedido/{idPedido}/total")
    public ResponseEntity<TotalPedidoDTO> calcularTotal(
            @PathVariable Integer idPedido) {
        TotalPedidoDTO total = detallesPedidoService.calcularTotalPedido(idPedido);
        return ResponseEntity.ok(total);
    }
}