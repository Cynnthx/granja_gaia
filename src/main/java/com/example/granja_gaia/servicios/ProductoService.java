package com.example.granja_gaia.servicios;

import com.example.granja_gaia.dtos.ProductoDTO;
import com.example.granja_gaia.modelos.Categoria;
import com.example.granja_gaia.modelos.Producto;
import com.example.granja_gaia.repositorios.CategoriaRepository;
import com.example.granja_gaia.repositorios.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    /**
     * Listar todos los productos.
     */
    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Listar productos por categoría.
     */
    public List<ProductoDTO> listarProductosPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoriaId(idCategoria)
                .stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtener un producto por su ID.
     */
    public Optional<ProductoDTO> obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id)
                .map(ProductoDTO::new);
    }

    /**
     * Filtrar productos por precio mínimo y máximo.
     */
    public List<ProductoDTO> filtrarProductosPorPrecio(double precioMin, double precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax)
                .stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Crear un nuevo producto.
     */
    public ProductoDTO crearProducto(Producto producto, Integer idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setCategoria(categoria);
        Producto productoGuardado = productoRepository.save(producto);
        return new ProductoDTO(productoGuardado);
    }

    /**
     * Editar un producto existente.
     */
    public Optional<ProductoDTO> editarProducto(Integer id, Producto productoDetalles) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoDetalles.getNombre());
                    producto.setDescripcion(productoDetalles.getDescripcion());
                    producto.setPrecio(productoDetalles.getPrecio());
                    producto.setImagenUrl(productoDetalles.getImagenUrl());
                    producto.setEspecificaciones(productoDetalles.getEspecificaciones());
                    producto.setStock(productoDetalles.getStock());
                    return new ProductoDTO(productoRepository.save(producto));
                });
    }

    /**
     * Eliminar un producto.
     */
    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }
}
