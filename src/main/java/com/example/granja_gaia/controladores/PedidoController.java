package com.example.granja_gaia.controladores;

import com.example.granja_gaia.dtos.PedidoDTO;
import com.example.granja_gaia.servicios.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // 1. Obtener todos los pedidos
    @GetMapping("/all")
    public ResponseEntity<List<PedidoDTO>> obtenerTodosLosPedidos() {
        List<PedidoDTO> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // 2. Obtener pedidos por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorCliente(
            @PathVariable Integer clienteId) {
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    // 3. Obtener pedidos por rango de fechas
    @GetMapping("/filtro")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorFecha(fechaInicio, fechaFin);
        return ResponseEntity.ok(pedidos);
    }

    // 4. Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedidoPorId(
            @PathVariable Integer id) {
        PedidoDTO pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    // 5. Crear un nuevo pedido
    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<PedidoDTO> crearPedido(
            @PathVariable Integer clienteId) {
        PedidoDTO nuevoPedido = pedidoService.crearPedido(clienteId);
        return ResponseEntity.ok(nuevoPedido);
    }

    // 6. Actualizar estado de un pedido
    @PutMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstadoPedido(
            @PathVariable Integer id,
            @RequestParam String estado) {
        PedidoDTO pedidoActualizado = pedidoService.actualizarEstadoPedido(id, estado);
        return ResponseEntity.ok(pedidoActualizado);
    }

    // 7. Eliminar un pedido (solo si está pendiente)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(
            @PathVariable Integer id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}