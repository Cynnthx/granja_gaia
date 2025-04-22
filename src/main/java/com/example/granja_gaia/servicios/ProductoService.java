package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.*;
import com.example.granja_gaia.modelos.Categoria;
import com.example.granja_gaia.modelos.Producto;
import com.example.granja_gaia.repositorios.CategoriaRepository;
import com.example.granja_gaia.repositorios.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    // =================== MÉTODOS PRINCIPALES ===================

    /**
     * Listar productos con filtros (categoría, precio, popularidad, ordenación).
     */
    public List<ProductoDTO> listarProductos(FiltroProductoDTO filtro) {
        List<Producto> productos;

        // 1. Filtrar por categoría (si se especifica)
        if (filtro.getIdCategoria() != null) {
            productos = productoRepository.findByCategoriaId(filtro.getIdCategoria());
        } else {
            productos = productoRepository.findAll();
        }

        // 2. Filtrar por rango de precio
        productos = productos.stream()
                .filter(p -> p.getPrecio() >= filtro.getPrecioMin() && p.getPrecio() <= filtro.getPrecioMax())
                .collect(Collectors.toList());

        // 3. Filtrar solo populares (si se solicita)
        if (filtro.getSoloPopulares() != null && filtro.getSoloPopulares()) {
            productos = productos.stream()
                    .filter(Producto::getEsPopular)
                    .collect(Collectors.toList());
        }

        // 4. Ordenar según criterio
        switch (filtro.getOrden()) {
            case "precio_asc":
                productos.sort(Comparator.comparing(Producto::getPrecio));
                break;
            case "precio_desc":
                productos.sort(Comparator.comparing(Producto::getPrecio).reversed());
                break;
            case "novedades":
                productos.sort(Comparator.comparing(Producto::getFechaCreacion).reversed());
                break;
            case "popularidad":
                productos.sort(Comparator.comparing(Producto::getEsPopular).reversed()
                        .thenComparing(Producto::getFechaCreacion).reversed());
                break;
        }

        return productos.stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtener detalles completos de un producto por ID.
     * Devuelve ProductoDetalleDTO (con todos los campos, incluyendo especificaciones y fecha).
     */
    public ProductoDetalleDTO obtenerDetalleProducto(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return new ProductoDetalleDTO(producto);
    }

    /**
     * Crear un nuevo producto.
     */
    @Transactional
    public ProductoDTO crearProducto(CrearProductoDTO productoDTO) {
        // 1. Buscar la categoría
        Categoria categoria = categoriaRepository.findById(productoDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // 2. Crear el producto
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setImagenUrl(productoDTO.getImagenUrl());
        producto.setStock(productoDTO.getStock());
        producto.setCategoria(categoria); // Asignar la categoría completa
        producto.setEsPopular(productoDTO.isEsPopular());

        producto.setEspecificaciones(productoDTO.getEspecificaciones());

        // 3. Guardar y retornar DTO
        Producto productoGuardado = productoRepository.save(producto);
        return new ProductoDTO(productoGuardado);
    }
    /**
     * Guardar un producto en la base de datos.
     * @param producto
     * @return
     */
    @Transactional
    public ProductoDTO guardarProducto(Producto producto) {
        // Validar que la categoría exista
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setCategoria(categoria); // Asignar la categoría completa
        Producto productoGuardado = productoRepository.save(producto);
        return new ProductoDTO(productoGuardado);
    }

    /**
     * Actualizar un producto existente.
     */
    public ProductoDTO actualizarProducto(Integer id, ActualizarProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizar solo los campos no nulos del DTO
        if (productoDTO.getNombre() != null) producto.setNombre(productoDTO.getNombre());
        if (productoDTO.getDescripcion() != null) producto.setDescripcion(productoDTO.getDescripcion());
        if (productoDTO.getPrecio() != null) producto.setPrecio(productoDTO.getPrecio());
        if (productoDTO.getImagenUrl() != null) producto.setImagenUrl(productoDTO.getImagenUrl());
        if (productoDTO.getEspecificaciones() != null) producto.setEspecificaciones(productoDTO.getEspecificaciones());
        if (productoDTO.getStock() != null) producto.setStock(productoDTO.getStock());
        if (productoDTO.getEsPopular() != null) producto.setEsPopular(productoDTO.getEsPopular()); // Actualizar popularidad
        if (productoDTO.getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(productoDTO.getIdCategoria())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }

        return new ProductoDTO(productoRepository.save(producto));
    }

    /**
     * Eliminar un producto.
     */
    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    // =================== MÉTODOS ADICIONALES ===================

    /**
     * Listar productos marcados como populares.
     */
    public List<ProductoDTO> listarProductosPopulares() {
        return productoRepository.findByEsPopularTrue()
                .stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Marcar/desmarcar un producto como popular.
     */
    public ProductoDTO marcarComoPopular(Integer id, boolean esPopular) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setEsPopular(esPopular);
        return new ProductoDTO(productoRepository.save(producto));
    }
}