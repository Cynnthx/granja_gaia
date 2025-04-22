package com.example.granja_gaia.controladores;

import com.example.granja_gaia.dtos.*;
import com.example.granja_gaia.modelos.Producto;
import com.example.granja_gaia.servicios.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // =================== ENDPOINTS PRINCIPALES ===================

    // 1. Crear producto
    @PostMapping("/crear")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody CrearProductoDTO productoDTO) {
        ProductoDTO productoGuardado = productoService.crearProducto(productoDTO);
        return ResponseEntity.ok(productoGuardado);
    }


    // 2. Listar todos los productos (con filtros opcionales)
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoDTO>> listarProductos(
            @RequestParam(required = false) Integer idCategoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Boolean soloPopulares,
            @RequestParam(defaultValue = "novedades") String orden) {

        FiltroProductoDTO filtro = new FiltroProductoDTO();
        filtro.setIdCategoria(idCategoria);
        filtro.setPrecioMin(precioMin != null ? precioMin : 0);
        filtro.setPrecioMax(precioMax != null ? precioMax : Double.MAX_VALUE);
        filtro.setSoloPopulares(soloPopulares);
        filtro.setOrden(orden);

        List<ProductoDTO> productos = productoService.listarProductos(filtro);
        return ResponseEntity.ok(productos);
    }

    // 3. Obtener detalles de un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDetalleDTO> obtenerDetalleProducto(@PathVariable Integer id) {
        ProductoDetalleDTO producto = productoService.obtenerDetalleProducto(id);
        return ResponseEntity.ok(producto);
    }

    // 4. Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(
            @PathVariable Integer id,
            @RequestBody ActualizarProductoDTO actualizacion) {
        ProductoDTO producto = productoService.actualizarProducto(id, actualizacion);
        return ResponseEntity.ok(producto);
    }

    // 5. Eliminar producto (¡Este es el que faltaba!)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build(); // Código 204 (sin contenido)
    }

    // =================== ENDPOINTS ADICIONALES ===================

    // 6. Listar productos populares
    @GetMapping("/populares")
    public ResponseEntity<List<ProductoDTO>> listarProductosPopulares() {
        List<ProductoDTO> productos = productoService.listarProductosPopulares();
        return ResponseEntity.ok(productos);
    }

    // 7. Marcar/desmarcar como popular
    @PatchMapping("/{id}/popular")
    public ResponseEntity<ProductoDTO> marcarComoPopular(
            @PathVariable Integer id,
            @RequestParam Boolean esPopular) {
        ProductoDTO producto = productoService.marcarComoPopular(id, esPopular);
        return ResponseEntity.ok(producto);
    }


}